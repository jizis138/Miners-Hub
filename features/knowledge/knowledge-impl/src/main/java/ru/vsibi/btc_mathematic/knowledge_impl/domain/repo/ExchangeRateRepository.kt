package ru.vsibi.btc_mathematic.knowledge_impl.domain.repo

import ru.vsibi.btc_mathematic.knowledge_api.model.ExchangeRate
import ru.vsibi.btc_mathematic.util.CallResult

interface ExchangeRateRepository {

    suspend fun fetchExchangeRates() : CallResult<List<ExchangeRate>>

}