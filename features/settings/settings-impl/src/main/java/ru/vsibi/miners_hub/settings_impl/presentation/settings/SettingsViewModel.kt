/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.settings_impl.presentation.settings

import ru.vsibi.miners_hub.mvi.BaseViewModel
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.model.RequestParams

class SettingsViewModel(
    requestParams: RequestParams,
    rootRouter: RootRouter
) : BaseViewModel<SettingsState, SettingsEvent>(
    rootRouter, requestParams
){
    override fun firstState(): SettingsState = SettingsState()
}