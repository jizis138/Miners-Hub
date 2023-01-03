/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.vsibi.miners_hub.knowledge_impl.R
import ru.vsibi.miners_hub.knowledge_impl.databinding.FragmentIncomePropertiesBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.FragmentMinerSelectionBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.adapter.MinerSelectionAdapter
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.mapper.MinerSelectionMapper
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.IncomeModeEvent
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.IncomeModeState
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.IncomeModeViewModel
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.adapter.IncomeModeAdapter
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.fragmentViewBinding
import ru.vsibi.miners_hub.presentation.base.util.noEventsExpected
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.uikit.SpacingItemDecoration
import ru.vsibi.miners_hub.util.collectWhenStarted
import ru.vsibi.miners_hub.util.dp
import ru.vsibi.miners_hub.util.getPrintableText
import ru.vsibi.miners_hub.util.onClick

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
        adapter.items = state.miners.map { item ->
            item.isSelected =
                state.addedMiners.find { it.id == item.id
                        || getPrintableText(it.name) == getPrintableText(item.name) } != null
            item
        }
        adapter.notifyDataSetChanged()

        binding.progress.isVisible = state.isLoad
    }

    override fun onRecieveEvent(event: MinerSelectionEvent) = noEventsExpected()

}