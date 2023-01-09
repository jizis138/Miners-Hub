/**
 * Created by Dmitry Popov on 05.01.2023.
 */
package ru.vsibi.btc_mathematic.mining_api

import ru.vsibi.btc_mathematic.navigation.contract.NavigationContractApi
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.navigation.model.NoResult

interface MiningFeature {

    val navigationContract : NavigationContractApi<NoParams, NoResult>

}