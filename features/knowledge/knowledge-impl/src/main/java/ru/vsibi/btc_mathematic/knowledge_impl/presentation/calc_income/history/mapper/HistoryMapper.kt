package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.mapper

import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.knowledge_impl.domain.logic.CalculationInteractor.Companion.TH
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.model.HistoryViewItem
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.getCurrencySymbol
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

object HistoryMapper {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

    fun mapResultToHistoryItem(results: List<CalculationState.ReadyResult>): List<HistoryViewItem> {
        val sortResults = results.sortedBy {
            it.fromDate
        }.reversed()
        return sortResults.map {
            HistoryViewItem(
                fromDate = PrintableText.Raw(dateTimeFormatter.format(it.fromDate)),
                totalPower = PrintableText.Raw("${it.hashrate.div(TH).roundToInt()} TH"),
                incomePerMonth = PrintableText.Raw("${it.perMonth.roundToInt()} ${getCurrencySymbol(it.exchangeRate.currency)}"),
                originalItem = it,
                composition = PrintableText.Raw(StringBuilder().apply {
                    it.miners.forEachIndexed { index, miner ->
                        this.append(
                            "${miner.name} ${miner.schemas.first().hashrate.div(TH)}TH x${miner.count}" + if (index == it.miners.lastIndex) {
                                ""
                            } else {
                                "\n\n"
                            }
                        )
                    }
                }.toString())
            )
        }
    }
}