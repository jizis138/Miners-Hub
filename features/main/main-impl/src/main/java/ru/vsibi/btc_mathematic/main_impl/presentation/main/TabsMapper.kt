package ru.vsibi.btc_mathematic.main_impl.presentation.main

import android.view.MenuItem
import ru.vsibi.btc_mathematic.main_api.MainFeature
import ru.vsibi.btc_mathematic.main_impl.R

object TabsMapper {
    fun mapItemIdToTab(item: MenuItem) = when (item.itemId) {
        R.id.rates -> MainFeature.TabType.Rates
        R.id.mining -> MainFeature.TabType.Mining
        R.id.knowledge -> MainFeature.TabType.Knowledge
        R.id.settings -> MainFeature.TabType.Settings
        else -> null
    }

    fun mapTabToItemId(tab: MainFeature.TabType): Int = when (tab) {
        MainFeature.TabType.Rates -> R.id.rates
        MainFeature.TabType.Mining -> R.id.mining
        MainFeature.TabType.Settings -> R.id.settings
        MainFeature.TabType.Knowledge -> R.id.knowledge
    }
}