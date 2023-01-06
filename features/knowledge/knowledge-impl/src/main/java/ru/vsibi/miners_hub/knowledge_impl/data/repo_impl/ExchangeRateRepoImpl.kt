package ru.vsibi.miners_hub.knowledge_impl.data.repo_impl

import ru.vsibi.miners_hub.knowledge_impl.data.service.ExchangeRateService
import ru.vsibi.miners_hub.knowledge_api.model.ExchangeRate
import ru.vsibi.miners_hub.knowledge_impl.domain.repo.ExchangeRateRepository
import ru.vsibi.miners_hub.util.CallResult
import ru.vsibi.miners_hub.util.callForResult

class ExchangeRateRepoImpl (
    private val exchangeRateService: ExchangeRateService
        ) : ExchangeRateRepository {
    override suspend fun fetchExchangeRates(): CallResult<List<ExchangeRate>> = callForResult {
        exchangeRateService.getExchangeRates()
    }
}