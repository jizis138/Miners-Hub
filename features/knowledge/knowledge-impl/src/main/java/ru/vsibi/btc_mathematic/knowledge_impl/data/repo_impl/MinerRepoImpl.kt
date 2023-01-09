/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.data.repo_impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import ru.vsibi.btc_mathematic.core.coroutines.DispatchersProvider
import ru.vsibi.btc_mathematic.knowledge_impl.data.service.MinerService
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.MinerStorage
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.MinerRepository
import ru.vsibi.btc_mathematic.util.callForResult
import ru.vsibi.btc_mathematic.util.onSuccess

class MinerRepoImpl(private val minerService: MinerService, private val minerStorage: MinerStorage) : MinerRepository {

    private val minerSharedFlow: SharedFlow<List<Miner>?> = minerStorage.getMinerFlow()
        .shareIn(scope = CoroutineScope(DispatchersProvider.IO), started = SharingStarted.Eagerly, replay = 1)


    override suspend fun fetchMiners() = callForResult { minerService.getMinersList() }.onSuccess {
        minerStorage.saveMiners(it)
    }

    override fun observeMiners(): Flow<List<Miner>?> = minerSharedFlow

}