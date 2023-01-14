package ru.vsibi.btc_mathematic.knowledge_impl.domain.logic

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.vsibi.btc_mathematic.core.exceptions.NothingToFoundResponseException
import ru.vsibi.btc_mathematic.knowledge_api.model.*
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.DifficultyRepository
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.ExchangeRateRepository
import ru.vsibi.btc_mathematic.util.CallResult
import ru.vsibi.btc_mathematic.util.callForResult
import ru.vsibi.btc_mathematic.util.getOrNull
import ru.vsibi.btc_mathematic.util.getOrThrow
import kotlin.math.roundToInt

class CalculationInteractor(
    private val difficultyRepository: DifficultyRepository,
    private val exchangeRateRepository: ExchangeRateRepository
) {

    companion object {
        const val COIN_BTC = "BTC"
        const val DAY = 86400
        const val TH = 1000000000000
        const val k = 4294967296
    }

    suspend fun calculateBTCIncome(
        hashrate: Double,
        power: Double,
        electricityPrice: Price,
        miners: List<Miner>,
        withDelay: Boolean = true
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
        val exchangeRate = fetchBTCtoRoubleRate() ?: run {
            emit(CalculationState.Error(NothingToFoundResponseException()))
            return@flow
        }

        if (withDelay) {
            delay(100)
        }
        emit(CalculationState.Calculation())

        val powerPerDay = power * 24 / 1000
        val powerPerMonth = powerPerDay * 30
        val priceElectricityPerDay = powerPerDay * electricityPrice.value
        val priceElectricityPerMonth = powerPerMonth * electricityPrice.value

        val incomeBTC = (DAY * BLOCK_INCOME * hashrate) / (difficulty.value * k)
        val incomePerDay = incomeBTC * exchangeRate.value
        val incomePerMonth = incomePerDay * 30

        val totalPerDay = incomePerDay - priceElectricityPerDay
        val totalPerMonth = incomePerMonth - priceElectricityPerMonth
        if (withDelay) {
            delay(100)
        }
        emit(
            CalculationState.ReadyResult(
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
                incomePerMonth = incomePerMonth.roundToInt()
            )
        )

    }

    private suspend fun fetchBTCDifficulty(): Difficulty {
        val response = difficultyRepository.fetchDifficulties().getOrThrow()
        return response.find { it.coin == COIN_BTC } ?: throw NothingToFoundResponseException()
    }

    suspend fun fetchBTCtoRoubleRate(): ExchangeRate? {
        val response = exchangeRateRepository.fetchExchangeRates().getOrNull()
        return response?.find { it.currency == "RUB" }
    }
}