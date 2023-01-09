/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_api.model.Price
import ru.vsibi.btc_mathematic.knowledge_impl.domain.logic.CalculationInteractor
import ru.vsibi.btc_mathematic.knowledge_impl.domain.logic.CalculationInteractor.Companion.TH
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total.model.DetailViewItem
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total.model.ResultViewItem
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total.model.TotalViewItem
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.presentation.base.navigation.ShareTextNavigationContract
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.getPrintableRawText
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.math.roundToInt

class TotalViewModel(
    router: RootRouter,
    requestParams: RequestParams,
    private val params: KnowledgeFeature.TotalCalculationParams,
    private val calculationInteractor: CalculationInteractor
) : BaseViewModel<TotalState, TotalEvent>(
    router, requestParams
) {

    init {
        startCalculation()
    }

    private val shareNavigationContract = launcher(ShareTextNavigationContract)

    override fun firstState(): TotalState {
        return TotalState(
            items = listOf(),
            calculationText = null
        )
    }

    private fun startCalculation() {
        val totalHashrate: Double
        val totalPower: Double
        val miners: List<Miner>
        val electricityPrice: Price
        val minersSb = StringBuilder()

        when (params.mode) {
            is KnowledgeFeature.TotalCalculationMode.ParamsForCalculation -> {
                val params =
                    params.mode as KnowledgeFeature.TotalCalculationMode.ParamsForCalculation

                totalHashrate = params.miners.sumOf {
                    it.schemas.first().hashrate * it.count
                }.toDouble()

                totalPower = params.miners.sumOf {
                    it.schemas.first().power * it.count
                }.toDouble()

                miners = params.miners

                miners.forEach {
                    minersSb.append("${it.name} ${it.schemas.first().hashrate.div(TH)}TH x${it.count}\n\n")
                }
                electricityPrice = Price(params.electricityPrice, "RUB")

                viewModelScope.launch {
                    calculationInteractor
                        .calculateBTCIncome(
                            hashrate = totalHashrate,
                            power = totalPower,
                            electricityPrice = electricityPrice,
                            miners = miners
                        )
                        .collectLatest { calculationState ->
                            when (calculationState) {
                                is CalculationState.Start -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = PrintableText.Raw("Начали расчет")
                                        )
                                    }
                                }
                                is CalculationState.Calculation -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = PrintableText.Raw("Рассчитываем доходность")
                                        )
                                    }
                                }
                                is CalculationState.Error -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = PrintableText.Raw("Ошибка")
                                        )
                                    }
                                }
                                is CalculationState.FetchingDifficulty -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = PrintableText.Raw("Получаем сложность блока")
                                        )
                                    }
                                }
                                is CalculationState.FetchingExchangeRate -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = PrintableText.Raw("Получаем курс валют")
                                        )
                                    }
                                }
                                is CalculationState.ReadyResult -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = null,
                                            items = createResultList(
                                                totalHashrate.div(TH),
                                                calculationState.perDay.roundToInt(),
                                                calculationState.perMonth.roundToInt(),
                                                electricityPrice,
                                                totalPower,
                                                calculationState.incomePerMonth,
                                                calculationState.powerPerMonth,
                                                calculationState.pricePowerPerMonth,
                                                calculationState.exchangeRate.value,
                                                calculationState.blockIncome,
                                                calculationState.difficulty.value,
                                                minersSb
                                            )
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is KnowledgeFeature.TotalCalculationMode.WithReadyCalculation -> {
                val params =
                    params.mode as KnowledgeFeature.TotalCalculationMode.WithReadyCalculation

                totalHashrate = params.calculationResult.hashrate

                totalPower = params.calculationResult.power

                miners = params.calculationResult.miners

                miners.forEach {
                    minersSb.append("${it.name} ${it.schemas.first().hashrate.div(TH)}TH x${it.count}\n\n")
                }
                electricityPrice = params.calculationResult.electricityPrice

                updateState { state ->
                    state.copy(
                        calculationText = null,
                        items = createResultList(
                            totalHashrate.div(TH),
                            params.calculationResult.perDay.roundToInt(),
                            params.calculationResult.perMonth.roundToInt(),
                            electricityPrice,
                            totalPower,
                            params.calculationResult.incomePerMonth,
                            params.calculationResult.powerPerMonth,
                            params.calculationResult.pricePowerPerMonth,
                            params.calculationResult.exchangeRate.value,
                            params.calculationResult.blockIncome,
                            params.calculationResult.difficulty.value,
                            minersSb
                        )
                    )
                }
            }
        }
    }

    fun createResultList(
        totalHashrate: Any,
        perDay: Int,
        perMonth: Int,
        electricityPrice: Price,
        totalPower: Any,
        incomePerMonth: Int,
        powerPerMonth: Double,
        pricePowerPerMonth: Int,
        exchangeRate: Double,
        blockIncome: Double,
        difficulty: Double,
        miners: StringBuilder
    ): List<TotalViewItem> {
        return listOf(
            TotalViewItem.Results(
                items = listOf(
                    ResultViewItem(
                        title = PrintableText.Raw("Общая мощность фермы"),
                        totalValue = PrintableText.Raw(
                            "$totalHashrate TH"
                        )
                    ),
                    ResultViewItem(
                        title = PrintableText.Raw("Средний доход в день"),
                        totalValue = PrintableText.Raw("${perDay} ₽")
                    ),
                    ResultViewItem(
                        title = PrintableText.Raw("Средний доход в месяц"),
                        totalValue = PrintableText.Raw("${perMonth} ₽")
                    ),
                )
            ),
            TotalViewItem.Details(
                items = listOf(
                    DetailViewItem(
                        title = PrintableText.Raw("Стоимость электричества"),
                        description = PrintableText.Raw("${electricityPrice.value} ${electricityPrice.currency}")
                    ),
                    DetailViewItem(
                        title = PrintableText.Raw("Суммарное потребление"),
                        description = PrintableText.Raw("$totalPower Вт")
                    ),
                    DetailViewItem(
                        title = PrintableText.Raw("Грязный доход за месяц"),
                        description = PrintableText.Raw("${incomePerMonth} ₽")
                    ),
                    DetailViewItem(
                        title = PrintableText.Raw("Потребление электричества за месяц"),
                        description = PrintableText.Raw("${powerPerMonth} кВт")
                    ),
                    DetailViewItem(
                        title = PrintableText.Raw("Стоимость электричества за месяц"),
                        description = PrintableText.Raw("${pricePowerPerMonth} ₽")
                    ),
                    DetailViewItem(
                        title = PrintableText.Raw("Стоимость 1 BTC"),
                        description = PrintableText.Raw("${exchangeRate} ₽")
                    ),
                    DetailViewItem(
                        title = PrintableText.Raw("Награда за блок"),
                        description = PrintableText.Raw("${blockIncome} BTC")
                    ),
                    DetailViewItem(
                        title = PrintableText.Raw("Сложность сети"),
                        description = PrintableText.Raw("${difficulty}")
                    ),
                    DetailViewItem(
                        title = PrintableText.Raw("Погрешность расчета"),
                        description = PrintableText.Raw("До 5%")
                    ),
                    DetailViewItem(
                        title = PrintableText.Raw("Актуальность"),
                        description = PrintableText.Raw("До ${getDateTomorrow()} 00:00")
                    ),
                    DetailViewItem(
                        title = PrintableText.Raw("Состав фермы"),
                        description = PrintableText.Raw("$miners")
                    ),

                    )
            ),
            TotalViewItem.ShareCalculation
        )
    }

    fun expandClicked() {
        sendEvent(TotalEvent.ExpandClicked)
    }

    fun shareClicked() {
        val sharedText = StringBuilder()
        val details = currentViewState.items.filterIsInstance<TotalViewItem.Details>().last()
        val results = currentViewState.items.filterIsInstance<TotalViewItem.Results>().last()

        sharedText.append("Расчет доходности фермы от ${getDateToday()}\n\n")

        results.items.forEach { resultViewItem ->
            sharedText.append(
                "${getPrintableRawText(resultViewItem.title)} : ${
                    getPrintableRawText(
                        resultViewItem.totalValue
                    )
                } \n\n"
            )
        }

        details.items.forEach { detailViewItem ->
            sharedText.append(
                "${getPrintableRawText(detailViewItem.title)} : ${
                    getPrintableRawText(
                        detailViewItem.description
                    )
                } \n\n"
            )
        }

        shareNavigationContract.launch(
            ShareTextNavigationContract.Params(
                text = sharedText.toString()
            )
        )
    }

    fun doneClicked() {
        router.backTo(null)
    }
}

val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
val dateTimeFormatter = SimpleDateFormat("dd-MM-yyyy HH:ss")

fun getDateToday(): String {
    return dateTimeFormatter.format(Date.from(Instant.now()))
}

fun getDateTomorrow(): String {
    return dateFormatter.format(Date.from(Instant.now().plusMillis(86400000)))
}
