package ru.vsibi.momento.main_impl.presentation.main

import android.view.MenuItem
import ru.vsibi.momento.main_api.MainFeature
import ru.vsibi.momento.main_impl.R

object TabsMapper {
    fun mapItemIdToTab(item: MenuItem) = when (item.itemId) {
        R.id.notes -> MainFeature.TabType.Notes
        R.id.settings -> MainFeature.TabType.Settings
        else -> null
    }

    fun mapTabToItemId(tab: MainFeature.TabType): Int = when (tab) {
        MainFeature.TabType.Notes -> R.id.notes
        MainFeature.TabType.Settings -> R.id.settings
    }
}