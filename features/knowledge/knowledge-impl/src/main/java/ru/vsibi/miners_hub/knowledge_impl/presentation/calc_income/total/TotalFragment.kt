/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import ru.vsibi.miners_hub.knowledge_impl.R
import ru.vsibi.miners_hub.knowledge_impl.databinding.FragmentTotalBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.adapter.TotalAdapter
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.fragmentViewBinding
import ru.vsibi.miners_hub.presentation.base.util.noEventsExpected
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.uikit.SpacingItemDecoration
import ru.vsibi.miners_hub.util.dp
import ru.vsibi.miners_hub.util.onClick
import ru.vsibi.miners_hub.util.setPrintableTextOrGone

class TotalFragment : BaseFragment<TotalState, TotalEvent>(R.layout.fragment_total) {

    override val vm: TotalViewModel by viewModel(
        getParamsInterface = TotalNavigationContract.getParams
    )

    private val adapter = TotalAdapter(
        onExpandClicked = {
            vm.expandClicked()
        },
        onShareClicked = {
            vm.shareClicked()
        }
    )

    private val binding by fragmentViewBinding(FragmentTotalBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
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
    }

    override fun onUpdateState(state: TotalState) = with(binding){
        calculationState.setPrintableTextOrGone(state.calculationText)
        adapter.items = state.items
    }

    override fun onRecieveEvent(event: TotalEvent){
        when(event){
            is TotalEvent.ExpandClicked -> {
                adapter.notifyDataSetChanged()
            }
        }
    }

}