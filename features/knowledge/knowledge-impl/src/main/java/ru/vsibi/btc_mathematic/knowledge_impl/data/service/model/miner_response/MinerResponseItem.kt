package ru.vsibi.btc_mathematic.knowledge_impl.data.service.model.miner_response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class MinerResponseItem(
    @SerialName("algorithms")
    val algorithms: JsonObject?,
    @SerialName("brand")
    val brand: String,
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("specs")
    val specs: JsonObject?,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String
)