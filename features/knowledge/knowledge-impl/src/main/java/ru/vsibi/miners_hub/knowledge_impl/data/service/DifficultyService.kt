package ru.vsibi.miners_hub.knowledge_impl.data.service

import android.util.Log
import kotlinx.serialization.json.jsonObject
import ru.vsibi.miners_hub.knowledge_impl.data.service.model.DifficultyResponse
import ru.vsibi.miners_hub.knowledge_impl.data.service.model.miner_response.MinerResponseItem
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Difficulty
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Miner
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Schema
import ru.vsibi.miners_hub.network.client.NetworkClientFactory
import kotlin.math.roundToLong

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