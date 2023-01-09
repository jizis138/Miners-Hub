package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.create_universal_miner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract

object CreateUniversalNavigationContract :
    NavigationContract<CreateUniversalNavigationContract.Params, CreateUniversalNavigationContract.Result>(
        CreateUniversalMinerFragment::class
    ) {

    @Parcelize
    data class Params(
        val mode: Mode
    ) : Parcelable

    sealed class Mode : Parcelable {

        @Parcelize
        class Normal : Mode(), Parcelable

        @Parcelize
        class Edit(
            val miner: Miner
        ) : Mode(), Parcelable
    }

    @Parcelize
    data class Result(
        val miner: Miner
    ) : Parcelable
}
