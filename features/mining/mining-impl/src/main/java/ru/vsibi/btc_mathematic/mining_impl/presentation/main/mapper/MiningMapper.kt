package ru.vsibi.btc_mathematic.mining_impl.presentation.main.mapper

import ru.vsibi.btc_mathematic.core.exceptions.NothingToFoundResponseException
import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.knowledge_api.model.Farm
import ru.vsibi.btc_mathematic.mining_impl.R
import ru.vsibi.btc_mathematic.mining_impl.presentation.main.model.FarmViewItem
import ru.vsibi.btc_mathematic.util.CallResult
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.getCurrencySymbol
import kotlin.math.roundToInt

class MiningMapper(
    private val knowledgeFeature: KnowledgeFeature
) {

    suspend fun mapFarmsToViewItems(farms: List<Farm?>?): List<FarmViewItem> =
        farms?.mapNotNull { farm ->
            if (farm == null) return@mapNotNull null
            val totalHashrate = farm.miners.sumOf {
                it.schemas.firstOrNull()?.hashrate?.times(it.count) ?: 0
            }.toDouble()

            val totalPower = farm.miners.sumOf {
                it.schemas.firstOrNull()?.power?.times(it.count) ?: 0
            }.toDouble()

            when (val result = knowledgeFeature.calculateBTCIncome(
                usingViaBtc = farm.usingViaBtc,
                hashrate = totalHashrate,
                power = totalPower,
                electricityPrice = farm.electricityPrice,
                miners = farm.miners,
                needSaveToHistory = false
            )) {
                is CallResult.Error -> {
                    return@mapNotNull FarmViewItem(
                        id = farm.id,
                        title = PrintableText.Raw(farm.title),
                        totalPower = PrintableText.Raw("${totalHashrate.div(TH).roundToInt()} TH"),
                        incomePerDay = PrintableText.StringResource(R.string.error),
                        incomePerMonth = PrintableText.StringResource(R.string.error),
                        calculationState = CalculationState.Error(NothingToFoundResponseException()),
                        usingViaBtc = farm.usingViaBtc
                    )
                }
                is CallResult.Success -> {
                    return@mapNotNull FarmViewItem(
                        id = farm.id,
                        title = PrintableText.Raw(farm.title),
                        totalPower = PrintableText.Raw("${totalHashrate.div(TH).roundToInt()} TH"),
                        incomePerDay = PrintableText.Raw("${result.data.perDay.roundToInt()} ${getCurrencySymbol(farm.electricityPrice.currency)}"),
                        incomePerMonth = PrintableText.Raw("${result.data.perMonth.roundToInt()} ${getCurrencySymbol(farm.electricityPrice.currency)}"),
                        calculationState = result.data,
                        usingViaBtc = farm.usingViaBtc
                    )
                }
            }
        } ?: listOf()

    companion object {
        private const val TH = 1000000000000
    }

}