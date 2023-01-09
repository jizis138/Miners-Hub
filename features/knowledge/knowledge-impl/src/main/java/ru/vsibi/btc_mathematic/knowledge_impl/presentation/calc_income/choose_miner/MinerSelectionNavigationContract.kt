package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_miner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract

object MinerSelectionNavigationContract :
    NavigationContract<MinerSelectionNavigationContract.Params, MinerSelectionNavigationContract.Result>(MinerSelectionFragment::class) {

    @Parcelize
    data class Params(
        val addedMiners: List<Miner>
    ) : Parcelable

    sealed class Result : Parcelable {
        @Parcelize
        data class Success(
            val addedMiners: List<Miner>
        ) : Result()

        @Parcelize
        object Cancel : Result()
    }

}