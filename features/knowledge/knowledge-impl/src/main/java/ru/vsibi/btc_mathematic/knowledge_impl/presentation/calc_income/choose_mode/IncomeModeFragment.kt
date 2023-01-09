/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_mode

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.FragmentIncomeModeBinding
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_mode.adapter.IncomeModeAdapter
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.noEventsExpected
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.uikit.SpacingItemDecoration
import ru.vsibi.btc_mathematic.util.dp
import ru.vsibi.btc_mathematic.util.onClick

class IncomeModeFragment: BaseFragment<IncomeModeState, IncomeModeEvent>(R.layout.fragment_income_mode) {

    override val vm : IncomeModeViewModel by viewModel()

    private val binding by fragmentViewBinding (FragmentIncomeModeBinding::bind)

    private val adapter = IncomeModeAdapter()

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
    }

    override fun onUpdateState(state: IncomeModeState) {
        adapter.items = state.items
    }

    override fun onRecieveEvent(event: IncomeModeEvent) = noEventsExpected()

}