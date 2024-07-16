package ru.vsibi.btc_mathematic.mining_impl.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FarmTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val usingViaBtc : Boolean,
    val miners : List<MinerDB>,
    val electricityPrice : PriceDB
)