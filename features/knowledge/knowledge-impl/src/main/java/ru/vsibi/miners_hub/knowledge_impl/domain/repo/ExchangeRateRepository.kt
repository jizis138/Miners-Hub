package ru.vsibi.miners_hub.knowledge_impl.domain.repo

import ru.vsibi.miners_hub.knowledge_api.model.ExchangeRate
import ru.vsibi.miners_hub.util.CallResult

interface ExchangeRateRepository {

    suspend fun fetchExchangeRates() : CallResult<List<ExchangeRate>>

}