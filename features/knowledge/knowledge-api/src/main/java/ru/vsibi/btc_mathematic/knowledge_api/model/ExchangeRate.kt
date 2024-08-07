package ru.vsibi.btc_mathematic.knowledge_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ExchangeRate(
    val coin : String,
    val coinFullName : String,
    val currency: String,
    val value : Double
) : Parcelable