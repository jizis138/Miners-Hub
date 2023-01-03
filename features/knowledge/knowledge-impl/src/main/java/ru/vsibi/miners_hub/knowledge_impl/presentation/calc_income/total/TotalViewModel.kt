/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.vsibi.miners_hub.knowledge_impl.domain.logic.CalculationInteractor
import ru.vsibi.miners_hub.knowledge_impl.domain.logic.CalculationInteractor.Companion.TH
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.DetailViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.ResultViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.TotalViewItem
import ru.vsibi.miners_hub.mvi.BaseViewModel
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.model.RequestParams
import ru.vsibi.miners_hub.presentation.base.navigation.ShareTextNavigationContract
import ru.vsibi.miners_hub.util.PrintableText
import ru.vsibi.miners_hub.util.getPrintableRawText
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.math.roundToInt

class TotalViewModel(
    router: RootRouter,
    requestParams: RequestParams,
    private val params: TotalNavigationContract.Params,
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
        val totalHashrate = params.miners.sumOf {
            it.schemas.first().hashrate
        }.toDouble()

        val totalPower = params.miners.sumOf {
            it.schemas.first().power
        }.toDouble()

        val miners = StringBuilder()

        params.miners.forEach {
            miners.append("${it.name} ${it.schemas.first().hashrate.div(TH)}TH \n\n")
        }

        val electricityPrice = params.electricityPrice


        viewModelScope.launch {
            calculationInteractor
                .calculateBTCIncome(
                    hashrate = totalHashrate,
                    power = totalPower,
                    electricityPrice = electricityPrice
                )
                .collectLatest { calculationState ->
                    when (calculationState) {
                        is CalculationInteractor.CalculationState.Start -> {
                            updateState { state ->
                                state.copy(
                                    calculationText = PrintableText.Raw("Начали расчет")
                                )
                            }
                        }
                        is CalculationInteractor.CalculationState.Calculation -> {
                            updateState { state ->
                                state.copy(
                                    calculationText = PrintableText.Raw("Рассчитываем доходность")
                                )
                            }
                        }
                        is CalculationInteractor.CalculationState.Error -> {
                            updateState { state ->
                                state.copy(
                                    calculationText = PrintableText.Raw("Ошибка")
                                )
                            }
                        }
                        is CalculationInteractor.CalculationState.FetchingDifficulty -> {
                            updateState { state ->
                                state.copy(
                                    calculationText = PrintableText.Raw("Получаем сложность блока")
                                )
                            }
                        }
                        is CalculationInteractor.CalculationState.FetchingExchangeRate -> {
                            updateState { state ->
                                state.copy(
                                    calculationText = PrintableText.Raw("Получаем курс валют")
                                )
                            }
                        }
                        is CalculationInteractor.CalculationState.ReadyResult -> {
                            updateState { state ->
                                state.copy(
                                    calculationText = null,
                                    items = listOf(
                                        TotalViewItem.Results(
                                            items = listOf(
                                                ResultViewItem(
                                                    title = PrintableText.Raw("Общая мощность фермы"),
                                                    totalValue = PrintableText.Raw(
                                                        "${
                                                            totalHashrate.div(
                                                                TH
                                                            )
                                                        } TH"
                                                    )
                                                ),
                                                ResultViewItem(
                                                    title = PrintableText.Raw("Средний доход в день"),
                                                    totalValue = PrintableText.Raw("${calculationState.perDay.roundToInt()} ₽")
                                                ),
                                                ResultViewItem(
                                                    title = PrintableText.Raw("Средний доход в месяц"),
                                                    totalValue = PrintableText.Raw("${calculationState.perMonth.roundToInt()} ₽")
                                                ),
                                            )
                                        ),
                                        TotalViewItem.Details(
                                            items = listOf(
                                                DetailViewItem(
                                                    title = PrintableText.Raw("Стоимость электричества"),
                                                    description = PrintableText.Raw("$electricityPrice ₽")
                                                ),
                                                DetailViewItem(
                                                    title = PrintableText.Raw("Суммарное потребление"),
                                                    description = PrintableText.Raw("$totalPower кВт")
                                                ),
                                                DetailViewItem(
                                                    title = PrintableText.Raw("Грязный доход за месяц"),
                                                    description = PrintableText.Raw("${calculationState.incomePerMonth} ₽")
                                                ),
                                                DetailViewItem(
                                                    title = PrintableText.Raw("Потребление электричества за месяц"),
                                                    description = PrintableText.Raw("${calculationState.powerPerMonth} кВт")
                                                ),
                                                DetailViewItem(
                                                    title = PrintableText.Raw("Стоимость электричества за месяц"),
                                                    description = PrintableText.Raw("${calculationState.pricePowerPerMonth} ₽")
                                                ),
                                                DetailViewItem(
                                                    title = PrintableText.Raw("Стоимость 1 BTC"),
                                                    description = PrintableText.Raw("${calculationState.exchangeRate.value} ₽")
                                                ),
                                                DetailViewItem(
                                                    title = PrintableText.Raw("Награда за блок"),
                                                    description = PrintableText.Raw("${calculationState.blockIncome} BTC")
                                                ),
                                                DetailViewItem(
                                                    title = PrintableText.Raw("Сложность сети"),
                                                    description = PrintableText.Raw("${calculationState.difficulty.value}")
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
                                )
                            }
                        }
                    }
                }
        }
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
            sharedText.append("${getPrintableRawText(resultViewItem.title)} : ${getPrintableRawText(resultViewItem.totalValue)} \n\n")
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
}

val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
val dateTimeFormatter = SimpleDateFormat("dd-MM-yyyy HH:ss")

fun getDateToday(): String {
    return dateTimeFormatter.format(Date.from(Instant.now()))
}

fun getDateTomorrow(): String {
    return dateFormatter.format(Date.from(Instant.now().plusMillis(86400000)))
}
