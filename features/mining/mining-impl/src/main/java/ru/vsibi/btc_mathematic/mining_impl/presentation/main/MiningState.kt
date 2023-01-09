package ru.vsibi.btc_mathematic.mining_impl.presentation.main

import ru.vsibi.btc_mathematic.mining_impl.presentation.main.model.FarmViewItem
import ru.vsibi.btc_mathematic.uikit.LoadingState
import ru.vsibi.btc_mathematic.util.ErrorInfo

data class MiningState(
    val loadingState : LoadingState<ErrorInfo, List<FarmViewItem>>
)