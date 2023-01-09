package ru.vsibi.btc_mathematic.mining_impl.presentation.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.knowledge_api.model.Farm
import ru.vsibi.btc_mathematic.mining_impl.domain.logic.MiningInteractor
import ru.vsibi.btc_mathematic.mining_impl.presentation.main.mapper.MiningMapper
import ru.vsibi.btc_mathematic.mining_impl.presentation.main.model.FarmViewItem
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.uikit.LoadingState
import ru.vsibi.btc_mathematic.util.CallResult
import ru.vsibi.btc_mathematic.util.ErrorInfo

class MiningViewModel(
    router: RootRouter,
    requestParams: RequestParams,
    private val miningInteractor: MiningInteractor,
    private val knowledgeFeature: KnowledgeFeature,
    private val miningMapper: MiningMapper
) : BaseViewModel<MiningState, MiningEvent>(
    router, requestParams
) {

    init {
        subscribeFarms()
    }

    private var farms = mutableListOf<Farm>()

    private val createFarmLauncher =
        launcher(knowledgeFeature.createFarmNavigationContract) { result ->
            when (result) {
                KnowledgeFeature.IncomePropertiesResult.EmptyResult -> Unit
                is KnowledgeFeature.IncomePropertiesResult.FarmResult -> {
                    viewModelScope.launch {
                        miningInteractor.createFarm(result.farm).withErrorHandled {
                            refreshFarms()
                        }
                    }
                }
            }
        }

    private val totalCalculationLauncher = launcher(knowledgeFeature.totalCalculationLauncher)

    override fun firstState(): MiningState {
        return MiningState(loadingState = LoadingState.None)
    }

    fun subscribeFarms() {
        updateState { state ->
            state.copy(
                loadingState = LoadingState.Loading
            )
        }
        viewModelScope.launch {
            withTimeoutOrNull(5000) {
                val farms = miningInteractor.observeFarms().first()

                this@MiningViewModel.farms =
                    farms?.filterNotNull()?.toMutableList() ?: mutableListOf()

                val farmViewItems = miningMapper.mapFarmsToViewItems(farms)
                updateState { state ->
                    state.copy(
                        loadingState = LoadingState.Success(
                            data = farmViewItems
                        )
                    )
                }
            } ?: updateState { state ->
                state.copy(
                    loadingState = LoadingState.Error(ErrorInfo.createEmpty())
                )
            }
        }
    }

    fun refreshFarms() {
        updateState { state ->
            state.copy(
                loadingState = LoadingState.Loading
            )
        }
        viewModelScope.launch {
            val result = miningInteractor.refreshFarms()

            when (result) {
                is CallResult.Error -> {
                    updateState { state ->
                        state.copy(
                            loadingState = LoadingState.Error(
                                error = ErrorInfo.createEmpty()
                            )
                        )
                    }
                }
                is CallResult.Success -> {
                    this@MiningViewModel.farms =
                        result.data?.toMutableList() ?: mutableListOf()

                    val farmViewItems = miningMapper.mapFarmsToViewItems(farms)
                    updateState { state ->
                        state.copy(
                            loadingState = LoadingState.Success(
                                data = farmViewItems
                            )
                        )
                    }
                }
            }
        }
    }

    fun onDetailFarmClicked(miningViewItem: FarmViewItem) {
        val farm = farms.find { it.id == miningViewItem.id }

        when (miningViewItem.calculationState) {
            is CalculationState.Error -> {
                totalCalculationLauncher.launch(
                    KnowledgeFeature.TotalCalculationParams(
                        mode = KnowledgeFeature.TotalCalculationMode.ParamsForCalculation(
                            electricityPrice = farm?.electricityPrice?.value ?: 0.0,
                            currency = farm?.electricityPrice?.currency ?: "RUB",
                            miners = farm?.miners ?: listOf()
                        )
                    )
                )
            }
            is CalculationState.ReadyResult -> {
                totalCalculationLauncher.launch(
                    KnowledgeFeature.TotalCalculationParams(
                        mode = KnowledgeFeature.TotalCalculationMode.WithReadyCalculation(
                            calculationResult = miningViewItem.calculationState
                        )
                    )
                )
            }
        }
    }

    fun createFarm() {
        createFarmLauncher.launch(
            KnowledgeFeature.IncomePropertiesParams(
                mode = KnowledgeFeature.Mode.CreateFarm
            )
        )
    }

    fun onMenuClicked(farmViewItem: FarmViewItem) {
        sendEvent(MiningEvent.ShowMenuDialog(farmViewItem))
    }

    fun editFarmClicked(farmViewItem: FarmViewItem) {
        val farm = farms.find { it.id == farmViewItem.id }

        createFarmLauncher.launch(
            KnowledgeFeature.IncomePropertiesParams(
                mode = KnowledgeFeature.Mode.EditFarm(
                    farm ?: return
                )
            )
        )
    }

    fun removeFarmClicked(farmViewItem: FarmViewItem) {
        sendEvent(MiningEvent.ShowAcceptDialog(farmViewItem))
    }

    fun deleteFarm(farmViewItem: FarmViewItem) {
        viewModelScope.launch {
            miningInteractor.deleteFarm(farmViewItem.id).withErrorHandled {
                refreshFarms()
            }
        }
    }
}