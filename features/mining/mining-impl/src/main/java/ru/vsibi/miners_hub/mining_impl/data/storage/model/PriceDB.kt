package ru.vsibi.miners_hub.mining_impl.data.storage.model

import kotlinx.serialization.Serializable

@Serializable
data class PriceDB(
    val value : Double,
    val currency : String
)