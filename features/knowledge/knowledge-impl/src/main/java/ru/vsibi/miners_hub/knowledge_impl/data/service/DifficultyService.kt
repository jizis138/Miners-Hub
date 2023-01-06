package ru.vsibi.miners_hub.knowledge_impl.data.service

import ru.vsibi.miners_hub.knowledge_impl.data.service.model.DifficultyResponse
import ru.vsibi.miners_hub.knowledge_api.model.Difficulty
import ru.vsibi.miners_hub.network.client.NetworkClientFactory

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