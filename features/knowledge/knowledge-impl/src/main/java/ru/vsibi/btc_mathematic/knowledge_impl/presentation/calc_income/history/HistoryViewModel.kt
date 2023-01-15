/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.HistoryRepository
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.IncomePropertiesNavigationContract
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.mapper.HistoryMapper
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.model.HistoryViewItem
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total.TotalNavigationContract
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.uikit.LoadingState
import ru.vsibi.btc_mathematic.uikit.dataOrNull
import ru.vsibi.btc_mathematic.util.CallResult
import ru.vsibi.btc_mathematic.util.ErrorInfo

class HistoryViewModel(
    rootRouter: RootRouter, requestParams: RequestParams,
    private val historyRepository: HistoryRepository
) : BaseViewModel<HistoryState, HistoryEvent>(rootRouter, requestParams) {

    private val totalLauncher = launcher(TotalNavigationContract)

    private val incomePropertiesLauncher = launcher(IncomePropertiesNavigationContract){}

    override fun firstState(): HistoryState {
        return HistoryState(
            loadingState = LoadingState.None
        )
    }

    fun fetchHistory() {
        updateState { state ->
            state.copy(
                loadingState = LoadingState.Loading
            )
        }
        viewModelScope.launch {
            when (val result = historyRepository.getSavedCalculations()) {
                is CallResult.Error -> {
                    updateState { state ->
                        state.copy(
                            loadingState = LoadingState.Error(ErrorInfo.createEmpty())
                        )
                    }
                }
                is CallResult.Success -> {
                    updateState { state ->
                        state.copy(
                            loadingState = LoadingState.Success(HistoryMapper.mapResultToHistoryItem(result.data))
                        )
                    }
                }
            }

        }
    }

    fun onItemClicked(historyViewItem: HistoryViewItem) {
        totalLauncher.launch(
            KnowledgeFeature.TotalCalculationParams(
                mode = KnowledgeFeature.TotalCalculationMode.WithReadyCalculation(
                    calculationResult = historyViewItem.originalItem
                )
            )
        )
    }

    fun onRemoveClicked(historyViewItem: HistoryViewItem) {
        viewModelScope.launch {
            historyRepository.removeItem(historyViewItem.originalItem.id)
            updateState { state ->
                val newList = currentViewState.loadingState.dataOrNull?.filter { it.originalItem.id != historyViewItem.originalItem.id }
                state.copy(
                    loadingState = LoadingState.Success(newList ?: return@updateState state)
                )
            }
        }
    }

    fun onMenuClicked(historyViewItem: HistoryViewItem) {
        sendEvent(HistoryEvent.ShowHistoryMenuDialog(historyViewItem))
    }

    fun createCalculation() {
        incomePropertiesLauncher.launch(
            KnowledgeFeature.IncomePropertiesParams(
                mode = KnowledgeFeature.Mode.Normal
            )
        )
    }

    fun removeAllHistory() {
        viewModelScope.launch {
            historyRepository.removeAllItems()
            updateState { state ->
                state.copy(
                    loadingState = LoadingState.Success(listOf())
                )
            }
        }
    }

    fun removeAllClicked() {
        sendEvent(HistoryEvent.ShowAcceptDeleteAllDialog)
    }


}