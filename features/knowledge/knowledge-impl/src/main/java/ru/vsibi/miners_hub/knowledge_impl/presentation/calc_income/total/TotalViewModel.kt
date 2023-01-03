/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total

import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.ResultViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.TotalViewItem
import ru.vsibi.miners_hub.mvi.BaseViewModel
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.model.RequestParams
import ru.vsibi.miners_hub.util.PrintableText
import java.math.BigDecimal

class TotalViewModel(
    router: RootRouter,
    requestParams: RequestParams,
    private val params: TotalNavigationContract.Params
) : BaseViewModel<TotalState, TotalEvent>(
    router, requestParams
) {

    init {
        startCalculation()
    }

    override fun firstState(): TotalState {
        return TotalState(
            items = listOf()
        )
    }

    private val currencyRateForBTC = "1128433.42"
    private val btcPerDayPerTH = "0.00000372"

    private val dollarToRoubleRate = 64.65
//    private val roublesPerDayPerTH = btcPerDayPerTH * currencyRateForBTC

    private fun startCalculation() {
        val totalPower = params.miners.sumOf {
            it.schemas.first().hashrate
        }.div(1000000000000f).toDouble()

        val roublesPerDayPerTH = BigDecimal(btcPerDayPerTH).multiply(BigDecimal(currencyRateForBTC))
        val totalRoublesPerDay = roublesPerDayPerTH.multiply(
            BigDecimal(totalPower)
        )
        val totalRoublesPerMonth = totalRoublesPerDay.multiply(BigDecimal("30"))
        updateState { state ->
            state.copy(
                items = listOf(
                    TotalViewItem.Results(
                        items = listOf(
                            ResultViewItem(
                                title = PrintableText.Raw("Общая мощность фермы"),
                                totalValue = PrintableText.Raw("$totalPower TH")
                            ),
                            ResultViewItem(
                                title = PrintableText.Raw("Средний доход в день"),
                                totalValue = PrintableText.Raw("$totalRoublesPerDay ₽")
                            ),
                            ResultViewItem(
                                title = PrintableText.Raw("Средний доход в месяц"),
                                totalValue = PrintableText.Raw("$totalRoublesPerMonth ₽")
                            ),
                        )
                    )
                )
            )
        }
    }
}
