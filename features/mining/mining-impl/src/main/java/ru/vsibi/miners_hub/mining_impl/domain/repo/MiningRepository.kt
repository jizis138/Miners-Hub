package ru.vsibi.miners_hub.mining_impl.domain.repo

import kotlinx.coroutines.flow.Flow
import ru.vsibi.miners_hub.knowledge_api.model.Farm
import ru.vsibi.miners_hub.mining_impl.data.storage.model.FarmTable

interface MiningRepository {

    fun observeFarms(): Flow<List<Farm?>?>

    suspend fun addFarm(farm : Farm)

    suspend fun refreshFarms(): List<Farm>?

    suspend fun deleteFarm(id: Long)

    suspend fun deleteFarms()

}