package ru.vsibi.btc_mathematic.knowledge_impl.data.service

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import ru.vsibi.btc_mathematic.knowledge_api.model.ExchangeRate
import ru.vsibi.btc_mathematic.network.client.NetworkClientFactory

class ExchangeRateService (
    clientFactory: NetworkClientFactory,
) {

    private val client = clientFactory.create()

    suspend fun getExchangeRates(): List<ExchangeRate> {
        val response: JsonObject = client.runGet(
            path = "https://blockchain.info/ticker",
            useBaseUrl = false
        )

        return response.toMap().entries.map {
            ExchangeRate(
                coin = "BTC",
                coinFullName = "Bitcoin",
                currency = it.key,
                value = it.value.jsonObject["buy"].toString().toDouble()
            )
        }
    }
}
