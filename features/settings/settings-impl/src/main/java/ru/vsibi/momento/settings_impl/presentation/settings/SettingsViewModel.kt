/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.settings_impl.presentation.settings

import ru.vsibi.momento.mvi.BaseViewModel
import ru.vsibi.momento.navigation.RootRouter
import ru.vsibi.momento.navigation.model.RequestParams

class SettingsViewModel(
    requestParams: RequestParams,
    rootRouter: RootRouter
) : BaseViewModel<SettingsState, SettingsEvent>(
    rootRouter, requestParams
){
    override fun firstState(): SettingsState = SettingsState()
}