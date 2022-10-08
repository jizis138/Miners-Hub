package ru.vsibi.momento.main_api

import ru.vsibi.momento.navigation.RootRouter
import ru.vsibi.momento.navigation.model.DeepLinkNavigator
import ru.vsibi.momento.navigation.model.IDeepLinkResult

class DeepLinkNavigatorImpl(
    private val router: RootRouter,
    private val mainFeature: MainFeature
) : DeepLinkNavigator {

    override fun navigateToDeepLink(iDeepLinkResult: IDeepLinkResult) {
        when (val deepLinkResult = iDeepLinkResult as DeepLinkResult) {
            is DeepLinkResult.MainScreen -> mainFeature.returnToMainScreenAndOpenTab(deepLinkResult.tab)
            is DeepLinkResult.NewChain -> router.newChain(*deepLinkResult.screens.toTypedArray())
        }.let { }
    }
}