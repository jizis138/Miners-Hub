/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_api.model.Price
import ru.vsibi.btc_mathematic.knowledge_impl.R
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
import ru.vsibi.btc_mathematic.util.getCurrencySymbol
import ru.vsibi.btc_mathematic.util.getPrintableText
import ru.vsibi.btc_mathematic.util.kotlin.switchJob
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

    private val shareNavigationContract = launcher(ShareTextNavigationContract)

    private var calculateJob by switchJob()

    init {
        startCalculation()
    }

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
                electricityPrice = Price(params.electricityPrice, params.currency)

                calculateJob = viewModelScope.launch {
                    calculationInteractor
                        .calculateBTCIncome(
                            hashrate = totalHashrate,
                            power = totalPower,
                            electricityPrice = electricityPrice,
                            miners = miners,
                            manualExchangeRate = params.exchangeRate
                        )
                        .collectLatest { calculationState ->
                            when (calculationState) {
                                is CalculationState.Start -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = PrintableText.StringResource(R.string.calc_state_start)
                                        )
                                    }
                                }
                                is CalculationState.Calculation -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = PrintableText.StringResource(R.string.calc_state_calc)
                                        )
                                    }
                                }
                                is CalculationState.Error -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = PrintableText.StringResource(R.string.calc_state_error)
                                        )
                                    }
                                }
                                is CalculationState.FetchingDifficulty -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = PrintableText.StringResource(R.string.calc_state_complexity_block)
                                        )
                                    }
                                }
                                is CalculationState.FetchingExchangeRate -> {
                                    updateState { state ->
                                        state.copy(
                                            calculationText = PrintableText.StringResource(R.string.calc_state_rate)
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
                                                btcIncomePerMonth = calculationState.btcIncomePerMonth,
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
                            btcIncomePerMonth = params.calculationResult.btcIncomePerMonth,
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
        btcIncomePerMonth: Double,
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
                        title = PrintableText.StringResource(ru.vsibi.btc_mathematic.core.R.string.farm_power),
                        totalValue = PrintableText.Raw(
                            "$totalHashrate TH"
                        )
                    ),
                    ResultViewItem(
                        title = PrintableText.StringResource(ru.vsibi.btc_mathematic.core.R.string.farm_income_per_day),
                        totalValue = PrintableText.Raw("$perDay ${getCurrencySymbol(electricityPrice.currency)}")
                    ),
                    ResultViewItem(
                        title = PrintableText.StringResource(ru.vsibi.btc_mathematic.core.R.string.farm_income_per_month),
                        totalValue = PrintableText.Raw("$perMonth ${getCurrencySymbol(electricityPrice.currency)}")
                    ),
                )
            ),
            TotalViewItem.Details(
                items = listOf(
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.electricity_host),
                        description = PrintableText.Raw("${electricityPrice.value} ${getCurrencySymbol(electricityPrice.currency)}")
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.total_consumption),
                        description = PrintableText.StringResource(R.string.wt, "$totalPower")
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.gross_income),
                        description = PrintableText.Raw("${incomePerMonth} ${getCurrencySymbol(electricityPrice.currency)}")
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.btc_income_per_month),
                        description = PrintableText.Raw("$btcIncomePerMonth BTC")
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.electricity_consumption_per_month),
                        description = PrintableText.StringResource(R.string.kwt, "${powerPerMonth.roundToInt()}")
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.electricity_cost_per_month),
                        description = PrintableText.Raw("${pricePowerPerMonth} ${getCurrencySymbol(electricityPrice.currency)}")
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.btc_cost),
                        description = PrintableText.Raw("${exchangeRate} ${getCurrencySymbol(electricityPrice.currency)}")
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.block_reward),
                        description = PrintableText.Raw("${blockIncome} BTC")
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.network_complexity),
                        description = PrintableText.Raw("${difficulty}")
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.calc_innacuracy),
                        description = PrintableText.StringResource(R.string.up_to_five_percent)
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.relewante),
                        description = PrintableText.StringResource(R.string.until_date, getDateTomorrow())
                    ),
                    DetailViewItem(
                        title = PrintableText.StringResource(R.string.farm_composition),
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

    fun shareClicked(context: Context) {
        val sharedText = StringBuilder()
        val details = currentViewState.items.filterIsInstance<TotalViewItem.Details>().last()
        val results = currentViewState.items.filterIsInstance<TotalViewItem.Results>().last()

        sharedText.append(context.getString(R.string.calculation_from_date, getDateToday()))

        results.items.forEach { resultViewItem ->
            sharedText.append(
                "${context.getPrintableText(resultViewItem.title)} : ${
                    context.getPrintableText(
                        resultViewItem.totalValue
                    )
                } \n\n"
            )
        }

        details.items.forEach { detailViewItem ->
            sharedText.append(
                "${context.getPrintableText(detailViewItem.title)} : ${
                    context.getPrintableText(
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

    fun onStop() {
        calculateJob = null
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
