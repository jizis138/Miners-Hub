/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.data.service

import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import ru.vsibi.miners_hub.knowledge_impl.data.service.model.miner_response.MinerResponseItem
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Miner
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Schema
import ru.vsibi.miners_hub.network.client.NetworkClientFactory
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MinerService(
    clientFactory: NetworkClientFactory,
) {
    private val client = clientFactory.create()

    suspend fun getMinersList(): List<Miner> {
        val response: List<MinerResponseItem> = client.runGet(
            path = "v2/hardware"
        )
        return response.map {
            val itemSchemas = mutableListOf<Schema>()
            it.algorithms?.let { schemas ->
                schemas.entries.forEach {
                    Log.d("MinerService", "getMinersList: ${it.key} ${it.value}")
                    itemSchemas.add(
                        Schema(
                            algorithmName = it.key,
                            hashrate = it.value.jsonObject["speed"].toString().toDouble().roundToLong(),
                            power = it.value.jsonObject["power"].toString().toDouble().roundToLong(),
                        )
                    )
                }
            }
            Miner(
                id = 0,
                name = it.name,
                schemas = itemSchemas
            )
        }
    }
}