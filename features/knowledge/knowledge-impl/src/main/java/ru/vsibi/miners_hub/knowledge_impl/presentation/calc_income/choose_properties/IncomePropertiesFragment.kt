/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import ru.vsibi.miners_hub.knowledge_impl.R
import ru.vsibi.miners_hub.knowledge_impl.databinding.FragmentIncomePropertiesBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.adapter.IncomePropertiesAdapter
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.adapter.MinersAdapter
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.MinerViewItem
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.fragmentViewBinding
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.uikit.SpacingItemDecoration
import ru.vsibi.miners_hub.uikit.animation.SimpleCommonAnimator
import ru.vsibi.miners_hub.uikit.animation.SlideInDownAnimator
import ru.vsibi.miners_hub.util.dp
import ru.vsibi.miners_hub.util.onClick
import ru.vsibi.miners_hub.util.setPrintableText

class IncomePropertiesFragment :
    BaseFragment<IncomePropertiesState, IncomePropertiesEvent>(R.layout.fragment_income_properties) {

    override val vm: IncomePropertiesViewModel by viewModel(
        getParamsInterface = IncomePropertiesNavigationContract.getParams
    )

    private val binding by fragmentViewBinding(FragmentIncomePropertiesBinding::bind)

    private val removeListener: (MinerViewItem) -> Unit = { minerViewItem ->
        val oldList = minersAdapter.items
        val newList = oldList.filter {
            it.id != minerViewItem.id
        }

        minersAdapter.items = newList
        vm.onRemoveMinerClicked(minerViewItem)
    }

    private val minersAdapter = MinersAdapter(removeListener, onItemCountChanged = {
        vm.onItemCountChanged(requireContext(), it)
    })

    private val adapter = IncomePropertiesAdapter(
        minersAdapter,
        onElectricityPriceChanged = {
            vm.onElectricityPriceChanged(it)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = adapter
        list.addItemDecoration(SpacingItemDecoration { index, itemCount ->
            val verticalMargin = if (index == 0) {
                8.dp
            } else {
                12.dp
            }

            Rect(
                20.dp,
                verticalMargin,
                20.dp,
                12.dp,
            )
        })
        list.itemAnimator = null
        cancel.onClick {
            requireActivity().onBackPressed()
        }
        calculation.onClick {
            vm.onCalculationClicked()
        }
    }

    override fun onUpdateState(state: IncomePropertiesState) {
        if (state.needUpdateList) {
            adapter.items = state.items
        }

        binding.toolbarTitle.setPrintableText(state.toolbarTitle)
    }

    override fun onRecieveEvent(event: IncomePropertiesEvent) {
        when (event) {
            IncomePropertiesEvent.ShowChooseMinerDialog -> {
                ChooseMinerTypeDialog.create(
                    this@IncomePropertiesFragment,
                    onMinerClicked = {
                        vm.selectMinerClicked()
                    },
                    onUniversalClicked = {
                        vm.selectUniversalMinerClicked()
                    }
                ).show()
            }
        }
    }

}