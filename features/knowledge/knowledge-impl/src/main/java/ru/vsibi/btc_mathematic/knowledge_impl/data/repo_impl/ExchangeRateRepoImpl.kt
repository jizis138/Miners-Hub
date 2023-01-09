package ru.vsibi.btc_mathematic.knowledge_impl.data.repo_impl

import ru.vsibi.btc_mathematic.knowledge_impl.data.service.ExchangeRateService
import ru.vsibi.btc_mathematic.knowledge_api.model.ExchangeRate
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.ExchangeRateRepository
import ru.vsibi.btc_mathematic.util.CallResult
import ru.vsibi.btc_mathematic.util.callForResult

class ExchangeRateRepoImpl (
    private val exchangeRateService: ExchangeRateService
        ) : ExchangeRateRepository {
    override suspend fun fetchExchangeRates(): CallResult<List<ExchangeRate>> = callForResult {
        exchangeRateService.getExchangeRates()
    }
}