package ru.vsibi.btc_mathematic.mining_impl.presentation.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
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
import ru.vsibi.btc_mathematic.uikit.dataOrNull
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
                is KnowledgeFeature.IncomePropertiesResult.FarmEditResult -> {
                    viewModelScope.launch {
                        updateState { state ->
                            state.copy(
                                loadingState = LoadingState.Loading
                            )
                        }
                        miningInteractor.editFarm(result.farm)
                    }
                }

                is KnowledgeFeature.IncomePropertiesResult.FarmCreateResult -> {
                    viewModelScope.launch {
                        updateState { state ->
                            state.copy(
                                loadingState = LoadingState.Loading
                            )
                        }
                        miningInteractor.createFarm(result.farm)
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
            miningInteractor.observeFarms().distinctUntilChanged().collectLatest { farms ->
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
                            usingViaBtc = farm?.usingViaBtc ?: false,
                            electricityPrice = farm?.electricityPrice?.value ?: 0.0,
                            currency = farm?.electricityPrice?.currency ?: "RUB",
                            miners = farm?.miners ?: listOf(),
                            exchangeRate = null
                        )
                    )
                )
            }

            is CalculationState.ReadyResult -> {
                totalCalculationLauncher.launch(
                    KnowledgeFeature.TotalCalculationParams(
                        mode = KnowledgeFeature.TotalCalculationMode.WithReadyCalculation(
                            usingViaBtc = miningViewItem.usingViaBtc,
                            calculationResult = miningViewItem.calculationState
                        )
                    )
                )
            }

            is CalculationState.Calculation -> {}
            is CalculationState.FetchingDifficulty -> {}
            is CalculationState.FetchingExchangeRate -> {}
            is CalculationState.Start -> {}
        }
    }

    fun createFarm() {
        createFarmLauncher.launch(
            KnowledgeFeature.IncomePropertiesParams(
                mode = KnowledgeFeature.Mode.CreateFarm(usingViaBtc = true)
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
                    usingViaBtc = farmViewItem.usingViaBtc,
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
            miningInteractor.deleteFarm(farmViewItem.id)
            updateState { state ->
                state.copy(
                    loadingState = LoadingState.Success(
                        data = currentViewState.loadingState.dataOrNull?.filter { it.id != farmViewItem.id } ?: listOf()
                    )
                )
            }
        }
    }
}