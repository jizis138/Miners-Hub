/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.settings_api

import ru.vsibi.momento.navigation.contract.NavigationContractApi
import ru.vsibi.momento.navigation.model.NoParams
import ru.vsibi.momento.navigation.model.NoResult

interface SettingsFeature {

    val navigationContract: NavigationContractApi<NoParams, NoResult>

}