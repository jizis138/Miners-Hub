package ru.vsibi.momento.navigation.model

import ru.vsibi.momento.navigation.model.IDeepLinkResult

interface DeepLinkNavigator {
    fun navigateToDeepLink(iDeepLinkResult: IDeepLinkResult)
}