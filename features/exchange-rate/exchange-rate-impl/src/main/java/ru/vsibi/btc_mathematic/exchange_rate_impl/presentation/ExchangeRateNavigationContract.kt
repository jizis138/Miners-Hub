package ru.vsibi.btc_mathematic.exchange_rate_impl.presentation

import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.navigation.model.NoResult

object ExchangeRateNavigationContract :
    NavigationContract<NoParams, NoResult>(ExchangeRateFragment::class) {
}