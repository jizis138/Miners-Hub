package ru.vsibi.btc_mathematic.knowledge_impl.data.service

import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive
import ru.vsibi.btc_mathematic.core.exceptions.WrongServerResponseException
import ru.vsibi.btc_mathematic.knowledge_impl.data.service.model.DifficultyResponse
import ru.vsibi.btc_mathematic.knowledge_api.model.Difficulty
import ru.vsibi.btc_mathematic.network.client.NetworkClientFactory

class DifficultyService(
    clientFactory: NetworkClientFactory,
) {

    private val client = clientFactory.create()

    suspend fun getCoinsDifficulty(): List<Difficulty> {
        val response: List<DifficultyResponse>? = client.runGet(
            path = "https://api.minerstat.com/v2/coins",
            useBaseUrl = false
        )

        return response?.mapNotNull {
            if (it.difficulty.jsonPrimitive.isString) return@mapNotNull null
            Difficulty(
                coin = it.coin,
                value = it.difficulty.jsonPrimitive.double
            )
        } ?: throw WrongServerResponseException()
    }
}