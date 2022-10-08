/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.main_impl

import ru.vsibi.momento.main_api.MainFeature
import ru.vsibi.momento.main_impl.domain.logic.OuterTabNavigator
import ru.vsibi.momento.main_impl.presentation.main.MainNavigationContract
import ru.vsibi.momento.navigation.RootRouter

class MainFeatureImpl(
    private val router: RootRouter,
    private val outerTabNavigator: OuterTabNavigator,
) : MainFeature {
    override val mainNavigationContract = MainNavigationContract

    override fun returnToMainScreenAndOpenTab(tab: MainFeature.TabType) {
        router.backTo(null)
        outerTabNavigator.openTab(tab)
    }
}