package ru.vsibi.miners_hub.mining_impl.presentation.main

import android.os.Bundle
import android.view.View
import ru.vsibi.miners_hub.core.ui.dialog.AcceptDialog
import ru.vsibi.miners_hub.mining_impl.R
import ru.vsibi.miners_hub.mining_impl.databinding.FragmentMiningBinding
import ru.vsibi.miners_hub.mining_impl.presentation.main.adapter.MiningAdapter
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.fragmentViewBinding
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.uikit.isLoading
import ru.vsibi.miners_hub.uikit.renderLoadingState
import ru.vsibi.miners_hub.util.PrintableText
import ru.vsibi.miners_hub.util.onClick

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
                    title = PrintableText.Raw("Вы уверены что хотите удалить ферму?"),
                    onAcceptClicked = {
                        vm.deleteFarm(event.farmViewItem)
                    }
                ).show()

            is MiningEvent.ShowMenuDialog -> MiningBottomDialog.create(
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