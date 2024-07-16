/**
 * Created by Dmitry Popov on 05.01.2023.
 */
package ru.vsibi.btc_mathematic.mining_impl

import ru.vsibi.btc_mathematic.knowledge_api.model.Farm
import ru.vsibi.btc_mathematic.mining_api.MiningFeature
import ru.vsibi.btc_mathematic.mining_impl.domain.logic.MiningInteractor
import ru.vsibi.btc_mathematic.mining_impl.presentation.main.MiningNavigationContract
import ru.vsibi.btc_mathematic.util.callForResult
import ru.vsibi.btc_mathematic.util.onSuccess

class MiningFeatureImpl(private val miningInteractor: MiningInteractor) : MiningFeature {

    override val navigationContract = MiningNavigationContract

    override suspend fun saveFarm(farm: Farm) {
        callForResult {
            miningInteractor.createFarm(farm)
        }.onSuccess {
            miningInteractor.refreshFarms()
        }
    }

}