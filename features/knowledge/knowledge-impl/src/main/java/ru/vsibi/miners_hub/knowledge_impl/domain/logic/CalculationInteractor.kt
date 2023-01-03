package ru.vsibi.miners_hub.knowledge_impl.domain.logic

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Difficulty
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.ExchangeRate
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
        const val BLOCK_INCOME = 6.25
        const val TH = 1000000000000
        const val k = 4294967296
    }

    suspend fun calculateBTCIncome(hashrate: Double, power : Double, electricityPrice: Double) = flow<CalculationState> {
        delay(100)
        emit(CalculationState.FetchingDifficulty())
        val difficulty = fetchBTCDifficulty() ?: run {
            emit(CalculationState.Error())
            return@flow
        }

        delay(100)
        emit(CalculationState.FetchingExchangeRate())
        val exchangeRate = fetchBTCtoRoubleRate() ?: run {
            emit(CalculationState.Error())
            return@flow
        }

        delay(100)
        emit(CalculationState.Calculation())

        val powerPerDay = power * 24 / 1000
        val powerPerMonth = powerPerDay * 30
        val priceElectricityPerDay = powerPerDay * electricityPrice
        val priceElectricityPerMonth = powerPerMonth * electricityPrice

        val incomeBTC = (DAY * BLOCK_INCOME * hashrate) / (difficulty.value * k)
        val incomePerDay = incomeBTC * exchangeRate.value
        val incomePerMonth = incomePerDay * 30

        val totalPerDay = incomePerDay - priceElectricityPerDay
        val totalPerMonth = incomePerMonth - priceElectricityPerMonth
        delay(100)
        emit(
            CalculationState.ReadyResult(
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

    private suspend fun fetchBTCtoRoubleRate(): ExchangeRate? {
        val response = exchangeRateRepository.fetchExchangeRates().getOrNull()
        return response?.find { it.currency == "RUB" }
    }

    sealed class CalculationState {
        class Start : CalculationState()
        class FetchingDifficulty : CalculationState()
        class FetchingExchangeRate : CalculationState()
        class Calculation : CalculationState()
        data class ReadyResult(
            val perDay: Double,
            val perMonth: Double,
            val exchangeRate: ExchangeRate,
            val difficulty: Difficulty,
            val blockIncome : Double = BLOCK_INCOME,
            val powerPerMonth : Double,
            val incomePerMonth : Int,
            val pricePowerPerMonth : Int
        ) : CalculationState()

        class Error() : CalculationState()
    }
}