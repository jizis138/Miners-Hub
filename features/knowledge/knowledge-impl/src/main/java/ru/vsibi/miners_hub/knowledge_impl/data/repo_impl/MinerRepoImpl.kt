/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.data.repo_impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import ru.vsibi.miners_hub.core.coroutines.DispatchersProvider
import ru.vsibi.miners_hub.knowledge_impl.data.service.MinerService
import ru.vsibi.miners_hub.knowledge_impl.data.storage.MinerStorage
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Miner
import ru.vsibi.miners_hub.knowledge_impl.domain.repo.MinerRepository
import ru.vsibi.miners_hub.util.callForResult
import ru.vsibi.miners_hub.util.onSuccess

class MinerRepoImpl(private val minerService: MinerService, private val minerStorage: MinerStorage) : MinerRepository {

    private val minerSharedFlow: SharedFlow<List<Miner>?> = minerStorage.getMinerFlow()
        .shareIn(scope = CoroutineScope(DispatchersProvider.IO), started = SharingStarted.Eagerly, replay = 1)


    override suspend fun fetchMiners() = callForResult { minerService.getMinersList() }.onSuccess {
        minerStorage.saveMiners(it)
    }

    override fun observeMiners(): Flow<List<Miner>?> = minerSharedFlow

}