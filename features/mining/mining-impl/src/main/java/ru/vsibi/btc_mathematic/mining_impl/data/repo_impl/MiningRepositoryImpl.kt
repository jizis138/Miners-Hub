package ru.vsibi.btc_mathematic.mining_impl.data.repo_impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import ru.vsibi.btc_mathematic.core.coroutines.DispatchersProvider
import ru.vsibi.btc_mathematic.mining_impl.data.storage.MiningStorage
import ru.vsibi.btc_mathematic.knowledge_api.model.Farm
import ru.vsibi.btc_mathematic.mining_impl.domain.repo.MiningRepository

class MiningRepositoryImpl(private val miningStorage: MiningStorage) : MiningRepository {

    private val farmSharedFlow: SharedFlow<List<Farm>?> = miningStorage.getFarmsFlow()
        .shareIn(scope = CoroutineScope(DispatchersProvider.IO), started = SharingStarted.Eagerly, replay = 1)

    override fun observeFarms(): Flow<List<Farm>?> = farmSharedFlow

    override suspend fun addFarm(farm : Farm) {
        miningStorage.addFarm(farm)
    }

    override suspend fun refreshFarms() = miningStorage.getFarms()

    override suspend fun deleteFarm(id: Long) {
        miningStorage.deleteFarms()
    }

    override suspend fun deleteFarms() {
        miningStorage.deleteFarms()
    }

}