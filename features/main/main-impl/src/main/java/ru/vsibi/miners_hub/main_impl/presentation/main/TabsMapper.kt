package ru.vsibi.miners_hub.main_impl.presentation.main

import android.view.MenuItem
import ru.vsibi.miners_hub.main_api.MainFeature
import ru.vsibi.miners_hub.main_impl.R

object TabsMapper {
    fun mapItemIdToTab(item: MenuItem) = when (item.itemId) {
        R.id.feed -> MainFeature.TabType.Notes
        R.id.rates -> MainFeature.TabType.Settings
        R.id.chats -> MainFeature.TabType.Settings
        R.id.knowledge -> MainFeature.TabType.Knowledge
        R.id.settings -> MainFeature.TabType.Settings
        else -> null
    }

    fun mapTabToItemId(tab: MainFeature.TabType): Int = when (tab) {
        MainFeature.TabType.Notes -> R.id.feed
        MainFeature.TabType.Settings -> R.id.settings
        MainFeature.TabType.Knowledge -> R.id.knowledge
    }
}