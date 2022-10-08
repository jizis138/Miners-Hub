package ru.vsibi.momento.main_api

import kotlinx.parcelize.Parcelize
import ru.vsibi.momento.navigation.contract.CreateParamsInterface
import ru.vsibi.momento.navigation.model.IDeepLinkResult

sealed interface DeepLinkResult : IDeepLinkResult {
    @Parcelize
    data class NewChain(val screens: List<CreateParamsInterface>) : DeepLinkResult {

        constructor(vararg screens: CreateParamsInterface) : this(screens.toList())

        init {
            check(screens.isNotEmpty()) {
                "Return null from DeepLinkDelegate.handle, if deepLink can't be handled!"
            }
        }
    }

    @Parcelize
    data class MainScreen(val tab: MainFeature.TabType) : DeepLinkResult
}