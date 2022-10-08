/**
 * Created by Dmitry Popov on 22.05.2022.
 */
package ru.vsibi.momento.main_impl.presentation.main

import ru.vsibi.momento.main_api.MainFeature

data class MainState(
    val currentTab: MainFeature.TabType,
    val debugVisible: Boolean
)