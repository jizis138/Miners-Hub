package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties

import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract

object IncomePropertiesNavigationContract :
    NavigationContract<KnowledgeFeature.IncomePropertiesParams, KnowledgeFeature.IncomePropertiesResult>(IncomePropertiesFragment::class) {

}