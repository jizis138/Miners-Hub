/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.settings_impl

import kotlinx.coroutines.flow.Flow
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContractApi
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.navigation.model.NoResult
import ru.vsibi.btc_mathematic.settings_api.SettingsFeature
import ru.vsibi.btc_mathematic.settings_impl.domain.logic.LocaleManager
import ru.vsibi.btc_mathematic.settings_impl.presentation.settings.SettingsNavigationContract

class SettingsFeatureImpl(
    private val localeManager: LocaleManager
) : SettingsFeature {
    override val navigationContract: NavigationContractApi<NoParams, NoResult> = SettingsNavigationContract

    override val localeFlow: Flow<SettingsFeature.LocaleEvent> = localeManager.observeLocale()

    override suspend fun getSavedLocale(): String = localeManager.getSavedLocale()
}