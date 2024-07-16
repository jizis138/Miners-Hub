package ru.vsibi.btc_mathematic.knowledge_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Price(
    val value : Double,
    val currency : String
) : Parcelable