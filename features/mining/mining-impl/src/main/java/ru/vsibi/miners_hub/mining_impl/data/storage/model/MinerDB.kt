package ru.vsibi.miners_hub.mining_impl.data.storage.model

import kotlinx.serialization.Serializable

@Serializable
class MinerDB(
    val id: Long,
    val name: String,
    val algorithmName : String? = null,
    val hashrate: Long,
    val power: Long,
    val count : Int,
    val tag : String,
)