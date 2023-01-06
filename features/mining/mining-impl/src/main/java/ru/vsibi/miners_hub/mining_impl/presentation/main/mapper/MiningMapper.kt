package ru.vsibi.miners_hub.mining_impl.presentation.main.mapper

import ru.vsibi.miners_hub.knowledge_api.KnowledgeFeature
import ru.vsibi.miners_hub.knowledge_api.model.CalculationState
import ru.vsibi.miners_hub.knowledge_api.model.Farm
import ru.vsibi.miners_hub.mining_impl.presentation.main.model.FarmViewItem
import ru.vsibi.miners_hub.util.CallResult
import ru.vsibi.miners_hub.util.PrintableText
import ru.vsibi.miners_hub.util.getOrNull
import ru.vsibi.miners_hub.util.getOrThrow
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
                hashrate = totalHashrate,
                power = totalPower,
                electricityPrice = farm.electricityPrice,
                miners = farm.miners
            )) {
                is CallResult.Error -> {
                    return@mapNotNull FarmViewItem(
                        id = farm.id,
                        title = PrintableText.Raw(farm.title),
                        totalPower = PrintableText.Raw("${totalHashrate.div(TH)} TH"),
                        incomePerDay = PrintableText.Raw("Ошибка"),
                        incomePerMonth = PrintableText.Raw("Ошибка"),
                        calculationState = CalculationState.Error()
                    )
                }
                is CallResult.Success -> {
                    return@mapNotNull FarmViewItem(
                        id = farm.id,
                        title = PrintableText.Raw(farm.title),
                        totalPower = PrintableText.Raw("${totalHashrate.div(TH)} TH"),
                        incomePerDay = PrintableText.Raw("${result.data.perDay.roundToInt()} ₽"),
                        incomePerMonth = PrintableText.Raw("${result.data.perMonth.roundToInt()} ₽"),
                        calculationState = result.data
                    )
                }
            }
        } ?: listOf()

    companion object {
        private const val TH = 1000000000000
    }

}