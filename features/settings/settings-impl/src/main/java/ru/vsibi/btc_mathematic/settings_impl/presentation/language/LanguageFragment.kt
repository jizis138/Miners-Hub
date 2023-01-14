/**
 * Created by Dmitry Popov on 14.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.language

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.mvi.BaseViewEvent
import ru.vsibi.btc_mathematic.mvi.ViewModelInterface
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.noEventsExpected
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.settings_impl.R
import ru.vsibi.btc_mathematic.settings_impl.databinding.FragmentLanguageBinding
import ru.vsibi.btc_mathematic.settings_impl.presentation.language.adapter.LanguageAdapter
import ru.vsibi.btc_mathematic.uikit.SpacingItemDecoration
import ru.vsibi.btc_mathematic.util.dp

class LanguageFragment : BaseFragment<LanguageState, Nothing>(R.layout.fragment_language) {

    override val vm: LanguageViewModel by viewModel()

    private val binding by fragmentViewBinding(FragmentLanguageBinding::bind)

    private val adapter = LanguageAdapter(
        onItemClicked = {
            vm.onLocaleClicked(it)
        }
    )

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
    }

    override fun onUpdateState(state: LanguageState) {
        adapter.items = state.items
    }

    override fun onRecieveEvent(event: Nothing) = noEventsExpected()

}