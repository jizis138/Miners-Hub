package ru.vsibi.momento.main_impl.presentation.main

import ru.vsibi.momento.navigation.contract.NavigationContract
import ru.vsibi.momento.navigation.model.NoParams
import ru.vsibi.momento.navigation.model.NoResult

object MainNavigationContract : NavigationContract<NoParams, NoResult>(MainFragment::class)