/**
 * Created by Dmitry Popov on 05.01.2023.
 */
package ru.vsibi.miners_hub.mining_impl

import ru.vsibi.miners_hub.mining_api.MiningFeature
import ru.vsibi.miners_hub.mining_impl.presentation.main.MiningNavigationContract

class MiningFeatureImpl : MiningFeature {

    override val navigationContract = MiningNavigationContract

}