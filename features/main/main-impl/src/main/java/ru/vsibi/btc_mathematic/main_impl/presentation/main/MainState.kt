/**
 * Created by Dmitry Popov on 22.05.2022.
 */
package ru.vsibi.btc_mathematic.main_impl.presentation.main

import ru.vsibi.btc_mathematic.main_api.MainFeature

data class MainState(
    val currentTab: MainFeature.TabType,
    val debugVisible: Boolean
)