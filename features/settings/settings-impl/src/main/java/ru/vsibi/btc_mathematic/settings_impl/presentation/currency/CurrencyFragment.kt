/**
 * Created by Dmitry Popov on 14.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.currency

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.noEventsExpected
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.settings_impl.R
import ru.vsibi.btc_mathematic.settings_impl.databinding.FragmentLanguageBinding
import ru.vsibi.btc_mathematic.settings_impl.presentation.currency.adapter.CurrencyAdapter
import ru.vsibi.btc_mathematic.uikit.SpacingItemDecoration
import ru.vsibi.btc_mathematic.uikit.animation.AddableItemAnimator
import ru.vsibi.btc_mathematic.uikit.animation.CommonItemAnimator
import ru.vsibi.btc_mathematic.uikit.animation.SimpleCommonAnimator
import ru.vsibi.btc_mathematic.uikit.animation.SlideInDownAnimator
import ru.vsibi.btc_mathematic.util.dp

class CurrencyFragment : BaseFragment<CurrencyState, Nothing>(R.layout.fragment_language) {

    override val vm: CurrencyViewModel by viewModel()

    private val binding by fragmentViewBinding(FragmentLanguageBinding::bind)

    private val adapter = CurrencyAdapter(
        onItemClicked = {
            vm.onCurrencyClicked(it)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
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
        list.adapter = adapter
    }

    override fun onUpdateState(state: CurrencyState) {
        adapter.items = state.items
    }

    override fun onRecieveEvent(event: Nothing) = noEventsExpected()

}