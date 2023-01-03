package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Miner
import ru.vsibi.miners_hub.navigation.contract.NavigationContract
import ru.vsibi.miners_hub.navigation.model.NoResult

object TotalNavigationContract : NavigationContract<TotalNavigationContract.Params, NoResult>(TotalFragment::class) {

    @Parcelize
    data class Params(
        val electricityPrice : Double,
        val currency : String,
        val miners : List<Miner>
    ) : Parcelable
}