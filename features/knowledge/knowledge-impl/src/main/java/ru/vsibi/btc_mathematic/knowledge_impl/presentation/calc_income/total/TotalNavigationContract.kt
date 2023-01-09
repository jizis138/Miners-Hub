package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total

import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract
import ru.vsibi.btc_mathematic.navigation.model.NoResult

object TotalNavigationContract : NavigationContract<KnowledgeFeature.TotalCalculationParams, NoResult>(TotalFragment::class) {

}