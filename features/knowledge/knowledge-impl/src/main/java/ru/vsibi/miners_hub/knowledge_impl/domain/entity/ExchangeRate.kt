package ru.vsibi.miners_hub.knowledge_impl.domain.entity


data class ExchangeRate(
    val coin : String,
    val currency: String,
    val value : Double
)