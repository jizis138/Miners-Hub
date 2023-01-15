package ru.vsibi.btc_mathematic.mining_impl.presentation.main

import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.core.ui.dialog.AcceptDialog
import ru.vsibi.btc_mathematic.mining_impl.R
import ru.vsibi.btc_mathematic.mining_impl.databinding.FragmentMiningBinding
import ru.vsibi.btc_mathematic.mining_impl.presentation.main.adapter.MiningAdapter
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.uikit.isLoading
import ru.vsibi.btc_mathematic.uikit.renderLoadingState
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.onClick

class MiningFragment : BaseFragment<MiningState, MiningEvent>(R.layout.fragment_mining) {

    override val vm: MiningViewModel by viewModel()

    private val binding by fragmentViewBinding(FragmentMiningBinding::bind)

    private val adapter = MiningAdapter(
        onDetailClicked = {
            vm.onDetailFarmClicked(it)
        },
        onMenuClicked = {
            vm.onMenuClicked(it)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = adapter

        with(emptyContainer) {
            errorTitle.setText(R.string.farms_is_empty)
            errorDescription.setText(R.string.farms_is_empty_desc)
            emptyButton.setText(R.string.create_farm)
            emptyButton.onClick {
                vm.createFarm()
            }
        }

        swipeRefresher.setOnRefreshListener {
            vm.refreshFarms()
        }
    }

    override fun onUpdateState(state: MiningState) {
        binding.swipeRefresher.isRefreshing = state.loadingState.isLoading
        renderLoadingState(
            loadingState = state.loadingState,
            progressContainer = binding.progressContainer,
            errorContainer = binding.errorContainer.errorView,
            contentContainer = binding.list,
            emptyContainer = binding.emptyContainer,
            renderData = {
                adapter.items = it
            },
            onRetryClicked = {
                vm.refreshFarms()
            },
        )
    }

    override fun onRecieveEvent(event: MiningEvent) {
        when (event) {
            is MiningEvent.ShowAcceptDialog ->
                AcceptDialog.create(
                    this,
                    title = PrintableText.StringResource(R.string.accept_delete_farm_description),
                    onAcceptClicked = {
                        vm.deleteFarm(event.farmViewItem)
                    }
                ).show()

            is MiningEvent.ShowMenuDialog -> FarmDialog.create(
                this,
                onEditClicked = {
                    vm.editFarmClicked(event.farmViewItem)
                },
                onRemoveClicked = {
                    vm.removeFarmClicked(event.farmViewItem)
                }).show()
        }
    }
}