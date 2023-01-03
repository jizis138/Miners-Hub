package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Miner
import ru.vsibi.miners_hub.navigation.contract.NavigationContract
import ru.vsibi.miners_hub.navigation.model.NoParams
import ru.vsibi.miners_hub.navigation.model.NoResult

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