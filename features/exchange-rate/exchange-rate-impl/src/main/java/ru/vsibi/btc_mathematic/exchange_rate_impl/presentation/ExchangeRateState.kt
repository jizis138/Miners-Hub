package ru.vsibi.btc_mathematic.exchange_rate_impl.presentation

import ru.vsibi.btc_mathematic.exchange_rate_impl.presentation.model.ExchangeRateViewItem
import ru.vsibi.btc_mathematic.uikit.LoadingState
import ru.vsibi.btc_mathematic.util.ErrorInfo

data class ExchangeRateState(
    val loadingState: LoadingState<ErrorInfo, List<ExchangeRateViewItem>>
)