/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.domain.logic

import kotlinx.coroutines.flow.Flow
import ru.vsibi.miners_hub.knowledge_api.model.Miner
import ru.vsibi.miners_hub.knowledge_impl.domain.repo.MinerRepository

class MinerInteractor(
    private val minerRepository: MinerRepository
) {

    suspend fun fetchMiners() = minerRepository.fetchMiners()

    suspend fun observeMiners(): Flow<List<Miner>?> {
        fetchMiners()
        return minerRepository.observeMiners()
    }

}