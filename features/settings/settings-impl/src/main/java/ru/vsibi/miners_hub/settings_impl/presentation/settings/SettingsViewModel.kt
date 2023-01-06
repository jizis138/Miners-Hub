/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.settings_impl.presentation.settings

import ru.vsibi.miners_hub.core.environment.Environment
import ru.vsibi.miners_hub.mvi.BaseViewModel
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.model.RequestParams
import ru.vsibi.miners_hub.util.PrintableText

class SettingsViewModel(
    requestParams: RequestParams,
    rootRouter: RootRouter,
    private val environment: Environment
) : BaseViewModel<SettingsState, SettingsEvent>(
    rootRouter, requestParams
){
    override fun firstState(): SettingsState = SettingsState(
        appVersion = PrintableText.Raw("Версия " + environment.appVersion + " от 06.01.2023")
    )
}