package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.create_universal_miner

import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.FragmentCreateUniversalMinerBinding
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.create_universal_miner.adapter.CreateMinerAdapter
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.noEventsExpected
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.util.onClick

class CreateUniversalMinerFragment :
    BaseFragment<CreateUniversalMinerState, CreateUniversalMinerEvent>(
        R.layout.fragment_create_universal_miner
    ) {

    override val vm: CreateUniversalMinerViewModel by viewModel()

    private val binding by fragmentViewBinding(FragmentCreateUniversalMinerBinding::bind)

    private val adapter = CreateMinerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = adapter

        ready.onClick {
            vm.onReadyClicked(requireContext(), adapter.items.last())
        }
    }

    override fun onUpdateState(state: CreateUniversalMinerState) {
        adapter.items = state.items
    }

    override fun onRecieveEvent(event: CreateUniversalMinerEvent) = noEventsExpected()


}