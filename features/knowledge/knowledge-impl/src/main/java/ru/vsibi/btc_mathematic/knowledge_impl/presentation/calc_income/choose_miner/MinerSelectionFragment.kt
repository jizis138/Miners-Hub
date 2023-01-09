/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_miner

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.FragmentMinerSelectionBinding
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_miner.adapter.MinerSelectionAdapter
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.noEventsExpected
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.util.onClick

class MinerSelectionFragment :
    BaseFragment<MinerSelectionState, MinerSelectionEvent>(R.layout.fragment_miner_selection) {

    override val vm: MinerSelectionViewModel by viewModel(
        getParamsInterface = MinerSelectionNavigationContract.getParams
    )

    private val binding by fragmentViewBinding(FragmentMinerSelectionBinding::bind)

    private val adapter = MinerSelectionAdapter(onItemClicked = {
        vm.onMinerClicked(it, context = requireContext())
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        inputSearch.doAfterTextChanged {
            vm.filter(requireContext(), it.toString())
        }
        list.adapter = adapter
        cancel.onClick {
            requireActivity().onBackPressed()
        }
        done.onClick {
            vm.onSelectReady(requireContext())
        }
    }

    override fun onUpdateState(state: MinerSelectionState) {
        adapter.items = state.miners
        binding.list.post {
            adapter.notifyDataSetChanged()
        }

        binding.progress.isVisible = state.isLoad
    }

    override fun onRecieveEvent(event: MinerSelectionEvent) = noEventsExpected()

}