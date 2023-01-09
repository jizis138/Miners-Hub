package ru.vsibi.btc_mathematic.mining_impl.domain.logic

import ru.vsibi.btc_mathematic.knowledge_api.model.Farm
import ru.vsibi.btc_mathematic.mining_impl.domain.repo.MiningRepository
import ru.vsibi.btc_mathematic.util.callForResult

class MiningInteractor(
    private val miningRepository: MiningRepository
) {

    fun observeFarms() = miningRepository.observeFarms()

    suspend fun refreshFarms() = callForResult {
        miningRepository.refreshFarms()
    }

    suspend fun createFarm(farm: Farm) = callForResult {
        miningRepository.deleteFarms()
        miningRepository.addFarm(farm)
    }

    suspend fun deleteFarm(id: Long) = callForResult{
        miningRepository.deleteFarm(id)
    }


}