/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.model

import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.util.PrintableText

data class HistoryViewItem(
    val fromDate : PrintableText,
    val totalPower : PrintableText,
    val incomePerMonth : PrintableText,
    val composition : PrintableText,
    val originalItem : CalculationState.ReadyResult,
)