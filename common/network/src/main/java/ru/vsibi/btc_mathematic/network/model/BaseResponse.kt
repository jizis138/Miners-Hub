package ru.vsibi.btc_mathematic.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse<T>(
    @SerialName("error")
    val error: ErrorResponse? = null,

    @SerialName("data")
    val data: T? = null,
)
