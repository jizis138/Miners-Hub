package ru.vsibi.miners_hub.mining_impl.presentation.main

import ru.vsibi.miners_hub.mining_impl.presentation.main.model.FarmViewItem
import ru.vsibi.miners_hub.uikit.LoadingState
import ru.vsibi.miners_hub.util.ErrorInfo

data class MiningState(
    val loadingState : LoadingState<ErrorInfo, List<FarmViewItem>>
)