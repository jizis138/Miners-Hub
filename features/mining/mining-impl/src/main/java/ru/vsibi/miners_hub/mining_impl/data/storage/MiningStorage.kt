package ru.vsibi.miners_hub.mining_impl.data.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vsibi.miners_hub.data.db.DatabaseFactory
import ru.vsibi.miners_hub.knowledge_api.model.Farm

class MiningStorage(
    databaseFactory: DatabaseFactory,
) {

    private val db by databaseFactory.create(MiningDatabase::class, "MiningDatabase")

    fun getFarmsFlow(): Flow<List<Farm>?> = db.miningDao
        .getFarmsFlow()
        .map { farmResult -> farmResult?.let(MiningDbMapper::mapFarms) }

    suspend fun getFarms() =
        db.miningDao.getFarms()?.let(MiningDbMapper::mapFarms)

    suspend fun addFarm(farm: Farm) = db.miningDao.addFarm(farm.let(MiningDbMapper::mapTableFarm))

    suspend fun deleteFarm(id: Long) = db.miningDao.removeFarm(id.toString())

    suspend fun deleteFarms() = db.miningDao.removeFarms()
}