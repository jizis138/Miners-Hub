package ru.vsibi.btc_mathematic.knowledge_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Farm(
    val id : Long,
    val title : String,
    val usingViaBtc : Boolean,
    val miners : List<Miner>,
    val electricityPrice : Price
) : Parcelable