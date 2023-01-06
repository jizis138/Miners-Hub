package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.create_universal_miner

import android.os.Bundle
import android.view.View
import ru.vsibi.miners_hub.knowledge_impl.R
import ru.vsibi.miners_hub.knowledge_impl.databinding.FragmentCreateUniversalMinerBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.create_universal_miner.adapter.CreateMinerAdapter
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.fragmentViewBinding
import ru.vsibi.miners_hub.presentation.base.util.noEventsExpected
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.util.onClick

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
            vm.onReadyClicked(adapter.items.last())
        }
    }

    override fun onUpdateState(state: CreateUniversalMinerState) {
        adapter.items = state.items
    }

    override fun onRecieveEvent(event: CreateUniversalMinerEvent) = noEventsExpected()


}