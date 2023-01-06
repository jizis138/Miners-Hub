package ru.vsibi.miners_hub.exchange_rate_impl.presentation

import ru.vsibi.miners_hub.exchange_rate_impl.presentation.model.ExchangeRateViewItem
import ru.vsibi.miners_hub.uikit.LoadingState
import ru.vsibi.miners_hub.util.ErrorInfo

data class ExchangeRateState(
    val loadingState: LoadingState<ErrorInfo, List<ExchangeRateViewItem>>
)