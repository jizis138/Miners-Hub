package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.miners_hub.knowledge_api.KnowledgeFeature
import ru.vsibi.miners_hub.knowledge_api.model.Miner
import ru.vsibi.miners_hub.navigation.contract.NavigationContract
import ru.vsibi.miners_hub.navigation.model.NoResult

object TotalNavigationContract : NavigationContract<KnowledgeFeature.TotalCalculationParams, NoResult>(TotalFragment::class) {

}