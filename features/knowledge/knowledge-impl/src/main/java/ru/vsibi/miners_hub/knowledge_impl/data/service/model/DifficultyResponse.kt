package ru.vsibi.miners_hub.knowledge_impl.data.service.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DifficultyResponse(
    @SerialName("algorithm")
    val algorithm: String,
    @SerialName("coin")
    val coin: String,
    @SerialName("difficulty")
    val difficulty: Double,
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("network_hashrate")
    val networkHashrate: Double,
    @SerialName("price")
    val price: Double,
    @SerialName("reward")
    val reward: Double,
    @SerialName("reward_block")
    val rewardBlock: Double,
    @SerialName("reward_unit")
    val rewardUnit: String,
    @SerialName("type")
    val type: String,
    @SerialName("updated")
    val updated: Int,
    @SerialName("volume")
    val volume: Double?
)