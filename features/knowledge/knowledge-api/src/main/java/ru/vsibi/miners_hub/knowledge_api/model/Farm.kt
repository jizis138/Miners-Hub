package ru.vsibi.miners_hub.knowledge_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Farm(
    val id : Long,
    val title : String,
    val miners : List<Miner>,
    val electricityPrice : Price
) : Parcelable