package ru.vsibi.btc_mathematic.main_impl.domain.logic

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.vsibi.btc_mathematic.main_api.MainFeature

class OuterTabNavigator {
    private val _tabToOpenFlow = Channel<MainFeature.TabType>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val tabToOpenFlow: Flow<MainFeature.TabType> = _tabToOpenFlow.receiveAsFlow()

    fun openTab(tab: MainFeature.TabType) {
        _tabToOpenFlow.trySend(tab)
    }
}