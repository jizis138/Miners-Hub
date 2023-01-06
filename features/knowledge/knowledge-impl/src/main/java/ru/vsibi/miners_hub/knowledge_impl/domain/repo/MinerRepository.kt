/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.domain.repo

import kotlinx.coroutines.flow.Flow
import ru.vsibi.miners_hub.knowledge_api.model.Miner
import ru.vsibi.miners_hub.util.CallResult

interface MinerRepository {

    suspend fun fetchMiners() : CallResult<List<Miner>>
    fun observeMiners(): Flow<List<Miner>?>

}