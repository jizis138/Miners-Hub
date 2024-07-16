/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vsibi.btc_mathematic.knowledge_api.model.*
import java.time.LocalDateTime

@Entity
class HistoryTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date : LocalDateTime,
    val hashrate: Double,
    val power: Double,
    val electricityPrice: Price,
    val miners : List<Miner>,
    val perDay: Double,
    val perMonth: Double,
    val exchangeRate: ExchangeRate,
    val difficulty: Difficulty,
    val blockIncome: Double = BLOCK_INCOME,
    val powerPerMonth: Double,
    val btcIncomePerDay : Double,
    val btcIncomePerMonth : Double,
    val incomePerMonth: Int,
    val pricePowerPerMonth: Int,
    val usingViaBtc : Boolean
)