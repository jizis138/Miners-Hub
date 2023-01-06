package ru.vsibi.miners_hub.knowledge_impl.domain.logic

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.vsibi.miners_hub.knowledge_api.model.*
import ru.vsibi.miners_hub.knowledge_impl.domain.repo.DifficultyRepository
import ru.vsibi.miners_hub.knowledge_impl.domain.repo.ExchangeRateRepository
import ru.vsibi.miners_hub.util.getOrNull
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
        miners : List<Miner>,
        withDelay: Boolean = true
    ) = flow<CalculationState> {
        if (withDelay) {
            delay(100)
        }
        emit(CalculationState.FetchingDifficulty())
        val difficulty = fetchBTCDifficulty() ?: run {
            emit(CalculationState.Error())
            return@flow
        }

        if (withDelay) {
            delay(100)
        }
        emit(CalculationState.FetchingExchangeRate())
        val exchangeRate = fetchBTCtoRoubleRate() ?: run {
            emit(CalculationState.Error())
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

    private suspend fun fetchBTCDifficulty(): Difficulty? {
        val response = difficultyRepository.fetchDifficulties().getOrNull()
        return response?.find { it.coin == COIN_BTC }
    }

    suspend fun fetchBTCtoRoubleRate(): ExchangeRate? {
        val response = exchangeRateRepository.fetchExchangeRates().getOrNull()
        return response?.find { it.currency == "RUB" }
    }
}