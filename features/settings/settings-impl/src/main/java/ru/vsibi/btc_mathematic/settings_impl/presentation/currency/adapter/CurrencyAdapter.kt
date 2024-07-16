/**
 * Created by Dmitry Popov on 14.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.currency.adapter

import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.settings_impl.databinding.HolderLanguageBinding
import ru.vsibi.btc_mathematic.settings_impl.presentation.currency.model.CurrencyViewItem
import ru.vsibi.btc_mathematic.util.*

class CurrencyAdapter(onItemClicked : (CurrencyViewItem) -> Unit) : AsyncListDifferDelegationAdapter<CurrencyViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createSettingsDelegate(onItemClicked)
    )
)

private fun createSettingsDelegate(onItemClicked: (CurrencyViewItem) -> Unit) = adapterDelegateViewBinding<CurrencyViewItem, HolderLanguageBinding>(
    HolderLanguageBinding::inflate
) {

    binding.root.onClick {
        onItemClicked(item)
    }

    bindWithBinding {
        title.setPrintableText(item.title)
        check.isVisible = item.isSelected
    }
}
