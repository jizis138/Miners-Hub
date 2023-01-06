/**
 * Created by Dmitry Popov on 05.01.2023.
 */
package ru.vsibi.miners_hub.mining_api

import ru.vsibi.miners_hub.navigation.contract.NavigationContractApi
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult

interface MiningFeature {

    val navigationContract : NavigationContractApi<NoParams, NoResult>

}