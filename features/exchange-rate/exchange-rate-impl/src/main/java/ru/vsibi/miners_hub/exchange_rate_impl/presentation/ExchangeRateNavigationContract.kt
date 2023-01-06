package ru.vsibi.miners_hub.exchange_rate_impl.presentation

import ru.vsibi.miners_hub.navigation.contract.NavigationContract
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult

object ExchangeRateNavigationContract :
    NavigationContract<NoParams, NoResult>(ExchangeRateFragment::class) {
}