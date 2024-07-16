package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total

sealed interface TotalEvent {

    object ExpandClicked : TotalEvent

    object ShareClicked : TotalEvent

    object SaveFarm : TotalEvent
}