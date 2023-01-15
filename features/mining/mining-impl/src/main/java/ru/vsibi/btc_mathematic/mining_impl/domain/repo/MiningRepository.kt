package ru.vsibi.btc_mathematic.mining_impl.domain.repo

import kotlinx.coroutines.flow.Flow
import ru.vsibi.btc_mathematic.knowledge_api.model.Farm

interface MiningRepository {

    fun observeFarms(): Flow<List<Farm?>?>

    suspend fun addFarm(farm : Farm)

    suspend fun refreshFarms(): List<Farm>?

    suspend fun deleteFarm(id: Long)

    suspend fun deleteFarms()

    suspend fun editFarm(farm: Farm)

}