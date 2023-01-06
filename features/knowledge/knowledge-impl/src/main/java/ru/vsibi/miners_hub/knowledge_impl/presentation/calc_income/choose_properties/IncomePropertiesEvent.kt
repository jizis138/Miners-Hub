package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties

sealed interface IncomePropertiesEvent {

    object ShowChooseMinerDialog : IncomePropertiesEvent

}