package ru.vsibi.miners_hub.knowledge_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Difficulty(
    val coin : String,
    val value : Double
) : Parcelable