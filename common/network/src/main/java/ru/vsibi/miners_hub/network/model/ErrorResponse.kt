package ru.vsibi.miners_hub.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ErrorResponse(
    @SerialName("statusCode")
    val code: Int,

    @SerialName("message")
    val message: String? = null,
)