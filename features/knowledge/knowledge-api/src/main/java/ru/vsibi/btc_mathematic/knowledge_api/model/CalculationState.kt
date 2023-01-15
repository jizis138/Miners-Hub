package ru.vsibi.btc_mathematic.knowledge_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

sealed class CalculationState : Parcelable {
    @Parcelize
    class Start : CalculationState(), Parcelable

    @Parcelize
    class FetchingDifficulty : CalculationState(), Parcelable

    @Parcelize
    class FetchingExchangeRate : CalculationState(), Parcelable

    @Parcelize
    class Calculation : CalculationState(), Parcelable

    /***
     * Используется как entity для всех результатов расчетов
     */
    @Parcelize
    data class ReadyResult(
        val id : Long,
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
        val btcIncomePerMonth : Double,
        val incomePerMonth: Int,
        val pricePowerPerMonth: Int,
        val fromDate : LocalDateTime,
    ) : CalculationState(), Parcelable

    @Parcelize
    class Error(
        val throwable: Throwable
    ) : CalculationState(), Parcelable
}