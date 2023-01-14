package ru.vsibi.btc_mathematic.knowledge_impl.data.service.model


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DifficultyResponse(
    @SerialName("algorithm")
    val algorithm: String,
    @SerialName("coin")
    val coin: String,
    @SerialName("difficulty")
    @Contextual
    val difficulty: JsonElement,
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