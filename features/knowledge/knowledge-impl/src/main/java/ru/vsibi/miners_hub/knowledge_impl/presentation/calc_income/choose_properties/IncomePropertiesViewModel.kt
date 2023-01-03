/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties

import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Miner
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.MinerSelectionNavigationContract
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.mapper.MinerMapper
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.TotalNavigationContract
import ru.vsibi.miners_hub.mvi.BaseViewModel
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.model.RequestParams
import ru.vsibi.miners_hub.util.PrintableText

class IncomePropertiesViewModel(
    router: RootRouter,
    requestParams: RequestParams,
    private val minerMapper: MinerMapper
) : BaseViewModel<IncomePropertiesState, IncomePropertiesEvent>(
    router, requestParams,
) {

    private var miners = mutableListOf<Miner>()

    private val minerSelectionLauncher = launcher(MinerSelectionNavigationContract) { result ->
        when (result) {
            MinerSelectionNavigationContract.Result.Cancel -> Unit
            is MinerSelectionNavigationContract.Result.Success -> {
                updateState { state ->
                    miners = result.addedMiners.toMutableList()
                    val addedMiners = minerMapper.mapMinersToViewItem(result.addedMiners)
                    val items = state.items

                    state.copy(items = items.map {
                        if (it is IncomePropertiesViewItem.MinerSelection) {
                            return@map it.copy(
                                items = addedMiners
                            )
                        }
                        it
                    })
                }
            }
        }
    }

    private val totalLauncher = launcher(TotalNavigationContract)

    override fun firstState(): IncomePropertiesState {
        return IncomePropertiesState(
            items = listOf(
                IncomePropertiesViewItem.MinerSelection(
                    title = PrintableText.Raw("Выберите майнеры"),
                    onClicked = {
                        val minerSelection = currentViewState.items.filterIsInstance<IncomePropertiesViewItem.MinerSelection>()

                        val selectedMiners = minerMapper.mapViewItemsToMiners(viewItems = minerSelection.last().items, allMiners = miners)
                        minerSelectionLauncher.launch(
                            MinerSelectionNavigationContract.Params(addedMiners = selectedMiners)
                        )
                    },
                    items = listOf()
                ),
                IncomePropertiesViewItem.ElectricitySelection(
                    title = PrintableText.Raw("Стоимость электричества"),
                )
            ),
        )
    }

    fun onCalculationClicked(){
        val electricitySelection = currentViewState.items.filterIsInstance<IncomePropertiesViewItem.ElectricitySelection>()
        val minerSelection = currentViewState.items.filterIsInstance<IncomePropertiesViewItem.MinerSelection>()

        totalLauncher.launch(TotalNavigationContract.Params(
            electricityPrice = electricitySelection.last().electricityPrice,
            currency = "₽",
            miners = minerMapper.mapViewItemsToMiners(
                viewItems = minerSelection.last().items,
                allMiners = miners
            )
        ))
    }

}
