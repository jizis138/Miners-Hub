package ru.vsibi.btc_mathematic.mining_impl.presentation.main

import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.navigation.model.NoResult

object MiningNavigationContract : NavigationContract<NoParams, NoResult>(MiningFragment::class) {
}