package ru.vsibi.btc_mathematic.knowledge_impl.data.service

import ru.vsibi.btc_mathematic.knowledge_impl.data.service.model.DifficultyResponse
import ru.vsibi.btc_mathematic.knowledge_api.model.Difficulty
import ru.vsibi.btc_mathematic.network.client.NetworkClientFactory

class DifficultyService(
    clientFactory: NetworkClientFactory,
    ) {

    private val client = clientFactory.create()

    suspend fun getCoinsDifficulty(): List<Difficulty> {
        val response: List<DifficultyResponse> = client.runGet(
            path = "https://api.minerstat.com/v2/coins",
            useBaseUrl = false
        )
        return response.map {
            Difficulty(
                coin = it.coin,
                value = it.difficulty
            )
        }
    }
}