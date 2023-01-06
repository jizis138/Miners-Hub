/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.vsibi.miners_hub.knowledge_api.model.Miner
import ru.vsibi.miners_hub.knowledge_impl.domain.logic.MinerInteractor
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.mapper.MinerSelectionMapper
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.model.MinerSelectionViewItem
import ru.vsibi.miners_hub.mvi.BaseViewModel
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.model.RequestParams
import ru.vsibi.miners_hub.util.getPrintableText

class MinerSelectionViewModel(
    router: RootRouter,
    requestParams: RequestParams,
    private val minerInteractor: MinerInteractor,
    private val params: MinerSelectionNavigationContract.Params,
    private val minerSelectionMapper : MinerSelectionMapper
) : BaseViewModel<MinerSelectionState, MinerSelectionEvent>(
    router, requestParams
) {

    init {
        viewModelScope.launch {
            fetchMiners()
        }
    }

    private var miners = mutableListOf<Miner>()
    private var selectionMiners = mutableListOf<MinerSelectionViewItem>()

    override fun firstState(): MinerSelectionState {
        return MinerSelectionState(
            miners = listOf(),
            isLoad = true,
            addedMiners = minerSelectionMapper.mapMinersToViewItem(
                params.addedMiners.distinct(),
                listOf()
            )
        )
    }

    private suspend fun fetchMiners() {
        minerInteractor.observeMiners().collectLatest { response ->
            updateState { it.copy(isLoad = false) }
            if (response == null) return@collectLatest
            miners = response.toMutableList()
            selectionMiners = response.filter {
                it.schemas.find { it.algorithmName == "SHA-256" } != null
            }.let {
                minerSelectionMapper.mapMinersToViewItem(it, currentViewState.addedMiners).toMutableList()
            }
            updateState {
                it.copy(
                    miners = selectionMiners
                )
            }
        }
    }

    fun filter(context: Context, input: String) {
        val filteredMiners = if (input.isEmpty()) {
            selectionMiners
        } else {
            selectionMiners.filter {
                context.getPrintableText(it.name).contains(input.trim(), true)
            }
        }
        updateState { state ->
            state.copy(
                miners = filteredMiners
            )
        }
    }

    fun onMinerClicked(minerSelectionViewItem: MinerSelectionViewItem, context: Context) {
        updateState { state ->
            val newList = if (minerSelectionViewItem.isSelected) {
                val addedList = state.addedMiners
                val minerToChangeCount = addedList.find {
                    /***
                     * TODO Заменить проверку на id
                     */
                    context.getPrintableText(it.name) == context.getPrintableText(minerSelectionViewItem.name)
                }
                if (minerToChangeCount == null) {
                    state.addedMiners.plus(minerSelectionViewItem)
                }else{
                    addedList.map {
                        //TODO Заменить проверку на id
                        if(context.getPrintableText(it.name) == context.getPrintableText(minerSelectionViewItem.name)){
                            minerSelectionViewItem
                        }else{
                            it
                        }
                    }
                }
            } else {
                state.addedMiners.filter {
                    //TODO Заменить проверку на id
                    context.getPrintableText(it.name) != context.getPrintableText(
                        minerSelectionViewItem.name
                    )
                }
            }
            state.copy(addedMiners = newList)
        }
    }

    fun onSelectReady(context: Context) {
        val addedMiners = currentViewState.addedMiners.mapNotNull { selectionItem ->
            miners.find { selectionItem.id == it.id || context.getPrintableText(selectionItem.name) == it.name }?.copy(
                count = selectionItem.count
            )
        }
        exitWithResult(
            MinerSelectionNavigationContract.createResult(
                MinerSelectionNavigationContract.Result.Success(
                    addedMiners = addedMiners
                )
            )
        )
    }

}
