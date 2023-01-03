/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.settings_impl

import ru.vsibi.miners_hub.navigation.contract.NavigationContractApi
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult
import ru.vsibi.miners_hub.settings_api.SettingsFeature
import ru.vsibi.miners_hub.settings_impl.presentation.settings.SettingsNavigationContract

class SettingsFeatureImpl : SettingsFeature {
    override val navigationContract: NavigationContractApi<NoParams, NoResult> = SettingsNavigationContract
}