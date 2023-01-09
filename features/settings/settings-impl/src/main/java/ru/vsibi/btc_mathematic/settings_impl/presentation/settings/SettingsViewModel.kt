/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.settings

import ru.vsibi.btc_mathematic.core.environment.Environment
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.util.PrintableText

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