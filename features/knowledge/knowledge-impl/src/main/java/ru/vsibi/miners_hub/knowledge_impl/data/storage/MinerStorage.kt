package ru.vsibi.miners_hub.knowledge_impl.data.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vsibi.miners_hub.data.db.DatabaseFactory
import ru.vsibi.miners_hub.knowledge_api.model.Miner

class MinerStorage(
    databaseFactory: DatabaseFactory,
) {

    private val db by databaseFactory.create(MinerDatabase::class, "MinerDatabase")

    fun getMinerFlow(): Flow<List<Miner>?> = db.minerDao
        .getMinersFlow()
        .map { notesResult -> notesResult?.let(MinerDbMapper::mapMiners) }

    suspend fun saveMiners(miners : List<Miner>) {
        db.minerDao.clear()
        db.minerDao.saveMiners(miners.let(MinerDbMapper::mapTableMiners))
    }
}