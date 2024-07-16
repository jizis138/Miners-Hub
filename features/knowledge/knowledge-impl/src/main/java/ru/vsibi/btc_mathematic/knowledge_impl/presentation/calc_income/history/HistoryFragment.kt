/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.core.ui.dialog.AcceptDialog
import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.FragmentHistoryBinding
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.adapter.HistoryAdapter
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.noEventsExpected
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.uikit.SpacingItemDecoration
import ru.vsibi.btc_mathematic.uikit.renderLoadingState
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.dp
import ru.vsibi.btc_mathematic.util.increaseHitArea
import ru.vsibi.btc_mathematic.util.onClick

class HistoryFragment : BaseFragment<HistoryState, HistoryEvent>(R.layout.fragment_history) {

    override val vm : HistoryViewModel by viewModel(getParamsInterface = HistoryNavigationContract.getParams)

    private val binding by fragmentViewBinding (FragmentHistoryBinding::bind)

    private val adapter = HistoryAdapter(
        onItemClicked = {
            vm.onItemClicked(it)
        },
        onMenuClicked = {
            vm.onMenuClicked(it)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)

        with(emptyContainer) {
            errorTitle.setText(R.string.calc_is_empty_title)
            errorDescription.setText(R.string.calc_is_empty_desc)
            emptyButton.setText(R.string.make_calc)
            emptyButton.onClick {
                vm.createCalculation()
            }
        }

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
        removeAll.increaseHitArea(16.dp)
        removeAll.onClick {
            vm.removeAllClicked()
        }
        cancel.onClick{
            requireActivity().onBackPressed()
        }

        vm.fetchHistory()
    }

    override fun onUpdateState(state: HistoryState) {
        renderLoadingState(
            loadingState = state.loadingState,
            progressContainer = binding.progressContainer,
            errorContainer = binding.errorContainer,
            contentContainer = binding.list,
            emptyContainer = binding.emptyContainer,
            onRetryClicked = {},
            renderData = {
                adapter.items = it
            }
        )
    }

    override fun onRecieveEvent(event: HistoryEvent) {
        when(event){
            is HistoryEvent.ShowHistoryMenuDialog -> {
                HistoryDialog.create(this, onRemoveClicked = {
                    vm.onRemoveClicked(event.historyViewItem)
                }).show()
            }
            HistoryEvent.ShowAcceptDeleteAllDialog -> {
                AcceptDialog.create(
                    this,
                    title = PrintableText.StringResource(R.string.accept_delete_all_history),
                    onAcceptClicked = {
                        vm.removeAllHistory()
                    }
                ).show()
            }
        }
    }


}