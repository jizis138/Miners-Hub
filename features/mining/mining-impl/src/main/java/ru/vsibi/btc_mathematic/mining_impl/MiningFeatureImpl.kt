/**
 * Created by Dmitry Popov on 05.01.2023.
 */
package ru.vsibi.btc_mathematic.mining_impl

import ru.vsibi.btc_mathematic.mining_api.MiningFeature
import ru.vsibi.btc_mathematic.mining_impl.presentation.main.MiningNavigationContract

class MiningFeatureImpl : MiningFeature {

    override val navigationContract = MiningNavigationContract

}