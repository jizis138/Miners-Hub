package ru.vsibi.btc_mathematic.knowledge_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class CalculationState : Parcelable {
    @Parcelize
    class Start : CalculationState(), Parcelable

    @Parcelize
    class FetchingDifficulty : CalculationState(), Parcelable

    @Parcelize
    class FetchingExchangeRate : CalculationState(), Parcelable

    @Parcelize
    class Calculation : CalculationState(), Parcelable

    @Parcelize
    data class ReadyResult(
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
        val incomePerMonth: Int,
        val pricePowerPerMonth: Int
    ) : CalculationState(), Parcelable

    @Parcelize
    class Error() : CalculationState(), Parcelable
}