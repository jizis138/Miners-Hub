/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.about_developer

import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams

class AboutDeveloperViewModel(
    rootRouter: RootRouter,
    requestParams: RequestParams
) : BaseViewModel<AboutDeveloperState, AboutDeveloperEvent>(rootRouter, requestParams) {

    override fun firstState(): AboutDeveloperState {
        return AboutDeveloperState()
    }

}