/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.settings_impl

import ru.vsibi.momento.navigation.contract.NavigationContractApi
import ru.vsibi.momento.navigation.model.NoParams
import ru.vsibi.momento.navigation.model.NoResult
import ru.vsibi.momento.settings_api.SettingsFeature
import ru.vsibi.momento.settings_impl.presentation.settings.SettingsNavigationContract

class SettingsFeatureImpl : SettingsFeature {
    override val navigationContract: NavigationContractApi<NoParams, NoResult> = SettingsNavigationContract
}