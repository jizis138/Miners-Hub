package ru.vsibi.btc_mathematic.knowledge_impl.domain.logic

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.vsibi.btc_mathematic.core.exceptions.NothingToFoundResponseException
import ru.vsibi.btc_mathematic.knowledge_api.model.*
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.DifficultyRepository
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.ExchangeRateRepository
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.HistoryRepository
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.ViaRepository
import ru.vsibi.btc_mathematic.util.CallResult
import ru.vsibi.btc_mathematic.util.callForResult
import ru.vsibi.btc_mathematic.util.getOrNull
import ru.vsibi.btc_mathematic.util.getOrThrow
import java.time.LocalDateTime
import kotlin.math.roundToInt

class CalculationInteractor(
    private val difficultyRepository: DifficultyRepository,
    private val exchangeRateRepository: ExchangeRateRepository,
    private val historyRepository: HistoryRepository,
    private val viaRepository: ViaRepository
) {

    companion object {
        const val COIN_BTC = "BTC"
        const val DAY = 86400
        const val TH = 1000000000000
        const val k = 4294967296
        const val TAG = "Calculation"
    }

    suspend fun calculateBTCIncome(
        usingViaBtc: Boolean,
        hashrate: Double,
        power: Double,
        electricityPrice: Price,
        miners: List<Miner>,
        withDelay: Boolean = true,
        needSaveToHistory: Boolean,
        manualExchangeRate: ExchangeRate?
    ) = flow<CalculationState> {
        if (withDelay) {
            delay(100)
        }
        emit(CalculationState.FetchingDifficulty())

        val difficulty = callForResult { fetchBTCDifficulty() }.run {
            when (this) {
                is CallResult.Error -> {
                    emit(CalculationState.Error(this.error))
                    return@flow
                }

                is CallResult.Success -> this.data
            }
        }


        if (withDelay) {
            delay(100)
        }
        emit(CalculationState.FetchingExchangeRate())
        val exchangeRate = manualExchangeRate ?: fetchBTCtoCurrencyRate(electricityPrice.currency)?.last() ?: run {
            emit(CalculationState.Error(NothingToFoundResponseException()))
            return@flow
        }
        Log.d(TAG, "calculateBTCIncome: rate $exchangeRate")
        Log.d(TAG, "calculateBTCIncome: common power $power")

        if (withDelay) {
            delay(100)
        }

        emit(CalculationState.Calculation())

        val incomeBTCPerDay = if (usingViaBtc) {
            callForResult { fetchViaIncomePerDay(
                coin = "BTC",
                coinPrice = fetchBTCtoCurrencyRate(electricityPrice.currency)?.lastOrNull()?.value?.toString() ?: run {
                    emit(CalculationState.Error(NothingToFoundResponseException()))
                    return@flow
                },
                difficulty = difficulty.value.toString(),
                hashrate = hashrate.div(TH).roundToInt().toString()
            ) }.run {
                when (this) {
                    is CallResult.Error -> {
                        emit(CalculationState.Error(this.error))
                        return@flow
                    }

                    is CallResult.Success -> this.data
                }
            }
        } else {
            (DAY * BLOCK_INCOME * hashrate) / (difficulty.value * k)
        }
        val powerPerDay = power * 24 / 1000
        Log.d(TAG, "calculateBTCIncome: powerPerDay $powerPerDay")

        val powerPerMonth = powerPerDay * 30
        Log.d(TAG, "calculateBTCIncome: powerPerMonth $powerPerMonth")

        val priceElectricityPerDay = powerPerDay * electricityPrice.value
        Log.d(TAG, "calculateBTCIncome: electricityPrice.value ${electricityPrice.value}")
        Log.d(TAG, "calculateBTCIncome: electricityPrice.currency ${electricityPrice.currency}")

        Log.d(TAG, "calculateBTCIncome: priceElectricityPerDay $powerPerMonth")

        val priceElectricityPerMonth = powerPerMonth * electricityPrice.value
        Log.d(TAG, "calculateBTCIncome: priceElectricityPerMonth $powerPerMonth")

        Log.d(TAG, "calculateBTCIncome: incomeBTC $powerPerMonth")
        val incomeBTCPerMonth = incomeBTCPerDay * 30

        val incomePerDay = incomeBTCPerDay * exchangeRate.value
        Log.d(TAG, "calculateBTCIncome: incomePerDay $powerPerMonth")

        val incomePerMonth = incomePerDay * 30
        Log.d(TAG, "calculateBTCIncome: incomePerMonth $powerPerMonth")

        val totalPerDay = incomePerDay - priceElectricityPerDay
        Log.d(TAG, "calculateBTCIncome: totalPerDay $powerPerMonth")

        val totalPerMonth = incomePerMonth - priceElectricityPerMonth
        Log.d(TAG, "calculateBTCIncome: totalPerMonth $powerPerMonth")

        if (withDelay) {
            delay(100)
        }
        val result =
            CalculationState.ReadyResult(
                id = -1,
                hashrate = hashrate,
                power = power,
                electricityPrice = electricityPrice,
                miners = miners,
                perDay = totalPerDay,
                perMonth = totalPerMonth,
                exchangeRate = exchangeRate,
                difficulty = difficulty,
                powerPerMonth = powerPerMonth,
                pricePowerPerMonth = priceElectricityPerMonth.roundToInt(),
                incomePerMonth = incomePerMonth.roundToInt(),
                btcIncomePerDay = incomeBTCPerDay,
                btcIncomePerMonth = incomeBTCPerMonth,
                fromDate = LocalDateTime.now(),
                usingViaBtc = usingViaBtc
            )
        if (needSaveToHistory) {
            historyRepository.saveCalculation(result)
        }
        emit(result)
    }

    private suspend fun fetchViaIncomePerDay(
        coin: String,
        coinPrice: String,
        difficulty: String,
        hashrate: String
    ): Double {
        return viaRepository.fetchIncomePerDay(
            coin, coinPrice, difficulty, hashrate)
    }

    private suspend fun fetchBTCDifficulty(): Difficulty {
        val response = difficultyRepository.fetchDifficulties().getOrThrow()
        return response.find { it.coin == COIN_BTC } ?: throw NothingToFoundResponseException()
    }

    suspend fun fetchBTCtoCurrencyRate(vararg currency: String): List<ExchangeRate>? {
        val response = exchangeRateRepository.fetchExchangeRates().getOrNull()
        val exchangeRates = currency.mapNotNull {
            response?.find { rates ->
                rates.currency == it
            }
        }

        return exchangeRates.ifEmpty { null }
    }
}