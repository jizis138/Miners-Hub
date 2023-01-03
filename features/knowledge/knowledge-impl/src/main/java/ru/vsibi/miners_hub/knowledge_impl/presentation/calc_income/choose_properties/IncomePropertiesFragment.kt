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
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.fragmentViewBinding
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.uikit.SpacingItemDecoration
import ru.vsibi.miners_hub.util.dp
import ru.vsibi.miners_hub.util.onClick

class IncomePropertiesFragment : BaseFragment<IncomePropertiesState, IncomePropertiesEvent>(R.layout.fragment_income_properties) {

    override val vm : IncomePropertiesViewModel by viewModel()

    private val binding by fragmentViewBinding (FragmentIncomePropertiesBinding::bind)

    private val adapter = IncomePropertiesAdapter()

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
        cancel.onClick {
            requireActivity().onBackPressed()
        }
        calculation.onClick{
            vm.onCalculationClicked()
        }
    }

    override fun onUpdateState(state: IncomePropertiesState) {
        adapter.items = state.items
    }

    override fun onRecieveEvent(event: IncomePropertiesEvent) {}

}