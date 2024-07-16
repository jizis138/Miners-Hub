package ru.vsibi.btc_mathematic.mining_impl.presentation.main.model

import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.util.PrintableText

data class FarmViewItem(
    val id: Long,
    val title: PrintableText,
    val usingViaBtc: Boolean,
    val totalPower: PrintableText,
    val incomePerDay: PrintableText,
    val incomePerMonth: PrintableText,
    val calculationState: CalculationState,
)