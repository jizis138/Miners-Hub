/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.FragmentTotalBinding
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total.adapter.TotalAdapter
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.uikit.SpacingItemDecoration
import ru.vsibi.btc_mathematic.util.dp
import ru.vsibi.btc_mathematic.util.onClick
import ru.vsibi.btc_mathematic.util.setPrintableTextOrGone

class TotalFragment : BaseFragment<TotalState, TotalEvent>(R.layout.fragment_total) {

    override val vm: TotalViewModel by viewModel(
        getParamsInterface = TotalNavigationContract.getParams
    )

    private val adapter = TotalAdapter(
        onExpandClicked = {
            vm.expandClicked()
        },
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

        done.onClick {
            vm.doneClicked()
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

            TotalEvent.ShareClicked ->
                vm.shareClicked(requireContext())

            TotalEvent.SaveFarm -> CreateFarmDialog.create(this@TotalFragment, onSaveClicked = {
                vm.saveFarm(it)
            }).show()
        }
    }

    override fun onStop() {
        super.onStop()
        vm.onStop()
    }

}