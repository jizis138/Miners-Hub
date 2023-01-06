package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.create_universal_miner

import ru.vsibi.miners_hub.knowledge_api.model.Miner
import ru.vsibi.miners_hub.knowledge_api.model.Schema
import ru.vsibi.miners_hub.knowledge_impl.domain.logic.CalculationInteractor
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.UniversalMinerViewItem
import ru.vsibi.miners_hub.mvi.BaseViewModel
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.model.RequestParams
import ru.vsibi.miners_hub.util.PrintableText

class CreateUniversalMinerViewModel(
    rootRouter: RootRouter,
    requestParams: RequestParams
) : BaseViewModel<CreateUniversalMinerState, CreateUniversalMinerEvent>(
    rootRouter, requestParams
) {

    override fun firstState(): CreateUniversalMinerState {
        return CreateUniversalMinerState(
            items = listOf(
                UniversalMinerViewItem(
                    id = System.currentTimeMillis()
                )
            )
        )
    }

    fun onReadyClicked(universalMinerViewItem: UniversalMinerViewItem) {
        exitWithResult(
            CreateUniversalNavigationContract.createResult(
                CreateUniversalNavigationContract.Result(
                    miner = Miner(
                        id = universalMinerViewItem.id,
                        name = universalMinerViewItem.name,
                        schemas = listOf(
                            Schema(
                                hashrate = universalMinerViewItem.hashrate * CalculationInteractor.TH,
                                power = universalMinerViewItem.power.toLong(),
                            )
                        ),
                        count = universalMinerViewItem.count,
                        tag = "Custom"
                    )
                )
            )
        )
    }
}