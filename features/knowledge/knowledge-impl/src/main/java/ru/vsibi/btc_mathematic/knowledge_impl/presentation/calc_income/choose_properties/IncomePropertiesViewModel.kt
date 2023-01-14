/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import kotlinx.parcelize.Parcelize
import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.knowledge_api.model.Farm
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_api.model.Price
import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_miner.MinerSelectionNavigationContract
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.mapper.MinerMapper
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model.MinerViewItem
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model.UniversalMinerViewItem
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.create_universal_miner.CreateUniversalNavigationContract
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total.TotalNavigationContract
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.mvi.provideSavedState
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.util.PrintableText

class IncomePropertiesViewModel(
    router: RootRouter,
    requestParams: RequestParams,
    private val minerMapper: MinerMapper,
    private val params: KnowledgeFeature.IncomePropertiesParams,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<IncomePropertiesState, IncomePropertiesEvent>(
    router, requestParams,
) {

    private var addedMiners = mutableListOf<Miner>()

    private var addedUniversalMiners = mutableListOf<Miner>()

    private var editingFarm: Farm? = null

    private val minerSelectionLauncher = launcher(MinerSelectionNavigationContract) { result ->
        when (result) {
            MinerSelectionNavigationContract.Result.Cancel -> Unit
            is MinerSelectionNavigationContract.Result.Success -> {
                onMinersAdded(result.addedMiners)
            }
        }
    }

    private val totalLauncher = launcher(TotalNavigationContract)

    private val createMinerLauncher = launcher(CreateUniversalNavigationContract) { result ->
        onUniversalMinerAdded(result.miner)
    }

    override fun firstState(): IncomePropertiesState {
        val savedState = savedStateHandle.provideSavedState {
            SavedState(
                addedMiners = addedMiners,
                electricityPrice = currentViewState.items.filterIsInstance<IncomePropertiesViewItem.ElectricitySelection>()
                    .lastOrNull()?.electricityPrice ?: 0.0
            )
        }
        val minerViewItems: List<MinerViewItem>
        val electricitySelection = IncomePropertiesViewItem.ElectricitySelection(
            title = PrintableText.StringResource(R.string.electricity_host),
        )
        if (params.mode is KnowledgeFeature.Mode.EditFarm) {
            val farm = (params.mode as KnowledgeFeature.Mode.EditFarm).farm
            editingFarm = farm
            electricitySelection.electricityPrice = farm.electricityPrice.value

            this.addedUniversalMiners = farm.miners.filter { it.tag == "Custom" }.toMutableList()
            this.addedMiners = farm.miners.filter { it.tag != "Custom" }.toMutableList()
            minerViewItems = minerMapper.mapMinersToViewItem(farm.miners)
        } else {
            minerViewItems = minerMapper.mapMinersToViewItem(savedState?.addedMiners ?: listOf())
        }
        val items = listOf(
            IncomePropertiesViewItem.MinerSelection(
                title = PrintableText.StringResource(R.string.select_miner),
                onClicked = {
                    sendEvent(IncomePropertiesEvent.ShowChooseMinerDialog)
                },
                items = minerViewItems
            ),
            electricitySelection
        )
        return IncomePropertiesState(
            items = items,
            toolbarTitle = when (params.mode) {
                KnowledgeFeature.Mode.CreateFarm -> PrintableText.StringResource(R.string.create_farm)
                is KnowledgeFeature.Mode.EditFarm -> PrintableText.StringResource(R.string.edit_farm)
                KnowledgeFeature.Mode.Normal -> PrintableText.StringResource(R.string.calc_income)
            },
            needUpdateList = true
        )
    }

    private fun onUniversalMinerAdded(miner: Miner) {
        addedUniversalMiners.add(miner)
        onMinersAdded(addedMiners)
    }

    private fun onMinersAdded(miners: List<Miner>) {
        updateState { state ->
            this.addedMiners = miners.toMutableList()
            val addedMiners =
                minerMapper.mapMinersToViewItem(miners.plus(addedUniversalMiners))

            val items = state.items

            state.copy(items = items.map {
                if (it is IncomePropertiesViewItem.MinerSelection) {
                    return@map it.copy(
                        items = addedMiners,
                    )
                }
                it
            }, needUpdateList = true)
        }
    }

    fun onCalculationClicked(farmTitle : String) {
        val electricitySelection =
            currentViewState.items.filterIsInstance<IncomePropertiesViewItem.ElectricitySelection>()

        val electricityPrice = electricitySelection.last().electricityPrice

        when (params.mode) {
            KnowledgeFeature.Mode.CreateFarm -> {
                exitWithResult(
                    IncomePropertiesNavigationContract.createResult(
                        KnowledgeFeature.IncomePropertiesResult.FarmResult(
                            farm = Farm(
                                id = System.currentTimeMillis(),
                                title = farmTitle,
                                miners = addedMiners.plus(addedUniversalMiners),
                                electricityPrice = Price(
                                    value = electricityPrice,
                                    currency = "₽"
                                )
                            )
                        )
                    )
                )
            }
            is KnowledgeFeature.Mode.EditFarm -> {
                exitWithResult(
                    IncomePropertiesNavigationContract.createResult(
                        KnowledgeFeature.IncomePropertiesResult.FarmResult(
                            farm = editingFarm?.copy(
                                title = farmTitle,
                                miners = addedMiners.plus(addedUniversalMiners),
                                electricityPrice = Price(
                                    value = electricityPrice,
                                    currency = "₽"
                                )
                            ) ?: return
                        )
                    )
                )
            }
            KnowledgeFeature.Mode.Normal -> {
                totalLauncher.launch(
                    KnowledgeFeature.TotalCalculationParams(
                        mode = KnowledgeFeature.TotalCalculationMode.ParamsForCalculation(
                            electricityPrice = electricityPrice,
                            currency = "₽",
                            miners = addedMiners.plus(addedUniversalMiners)
                        )
                    )
                )
            }
        }
    }

    fun onRemoveUniversalItemClicked(universalMinerViewItem: UniversalMinerViewItem) {
        updateState { state ->
            val universalSelection =
                state.items.filterIsInstance<IncomePropertiesViewItem.UniversalSelection>()

            val oldItems = universalSelection.last().items
            state.copy(
                items = state.items.map { incomePropertiesViewItem ->
                    if (incomePropertiesViewItem is IncomePropertiesViewItem.UniversalSelection) {
                        return@map incomePropertiesViewItem.copy(
                            items = oldItems.filter {
                                it.id != universalMinerViewItem.id
                            }
                        )
                    }
                    incomePropertiesViewItem
                }
            )
        }

    }

    fun selectMinerClicked() {
        minerSelectionLauncher.launch(
            MinerSelectionNavigationContract.Params(addedMiners = addedMiners)
        )
    }

    fun selectUniversalMinerClicked() {
        createMinerLauncher.launch(
            CreateUniversalNavigationContract.Params(
                mode = CreateUniversalNavigationContract.Mode.Normal()
            )
        )
    }

    fun onRemoveMinerClicked(minerViewItem: MinerViewItem) {
        val minerToRemove = addedMiners.find { it.id == minerViewItem.id }
        addedMiners.remove(minerToRemove)

        val minerUniversalToRemove = addedUniversalMiners.find { it.id == minerViewItem.id }
        addedUniversalMiners.remove(minerUniversalToRemove)

        updateState { state ->
            state.copy(
                items = state.items.map { incomePropertiesViewItem ->
                    if (incomePropertiesViewItem is IncomePropertiesViewItem.MinerSelection) {
                        return@map incomePropertiesViewItem.copy(
                            items = minerMapper.mapMinersToViewItem(addedMiners.plus(addedUniversalMiners))
                        )
                    }
                    incomePropertiesViewItem
                },
                needUpdateList = true
            )
        }
    }

    fun onItemCountChanged(context: Context, minerViewItem: MinerViewItem) {
        addedMiners = addedMiners.map {
            if (it.id == minerViewItem.id) {
                return@map it.copy(
                    count = minerViewItem.count
                )
            }
            it
        }.toMutableList()
        addedUniversalMiners = addedUniversalMiners.map {
            if (it.id == minerViewItem.id) {
                return@map it.copy(
                    count = minerViewItem.count
                )
            }
            it
        }.toMutableList()
        updateState { state ->
            state.copy(
                items = state.items.map { incomePropertiesViewItem ->
                    if (incomePropertiesViewItem is IncomePropertiesViewItem.MinerSelection) {
                        return@map incomePropertiesViewItem.copy(
                            items = minerMapper.mapMinersToViewItem(addedMiners.plus(addedUniversalMiners))
                        )
                    }
                    incomePropertiesViewItem
                },
                needUpdateList = false
            )
        }
    }

    fun onElectricityPriceChanged(electricitySelection: IncomePropertiesViewItem.ElectricitySelection) {
        updateState { state ->
            state.copy(
                items = state.items.map { incomePropertiesViewItem ->
                    if (incomePropertiesViewItem is IncomePropertiesViewItem.ElectricitySelection) {
                        return@map electricitySelection
                    }
                    incomePropertiesViewItem
                },
                needUpdateList = false
            )
        }
    }

    @Parcelize
    data class SavedState(
        val addedMiners: List<Miner>,
        val electricityPrice: Double
    ) : Parcelable

}
