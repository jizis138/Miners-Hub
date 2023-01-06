package ru.vsibi.miners_hub.mining_impl.presentation.main.model

import ru.vsibi.miners_hub.knowledge_api.model.CalculationState
import ru.vsibi.miners_hub.util.PrintableText

data class FarmViewItem(
    val id : Long,
    val title : PrintableText,
    val totalPower : PrintableText,
    val incomePerDay : PrintableText,
    val incomePerMonth : PrintableText,
    val calculationState : CalculationState
)