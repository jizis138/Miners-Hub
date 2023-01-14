/**
 * Created by Dmitry Popov on 14.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.language.adapter

import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.settings_impl.databinding.HolderLanguageBinding
import ru.vsibi.btc_mathematic.settings_impl.presentation.language.model.LanguageViewItem
import ru.vsibi.btc_mathematic.util.*

class LanguageAdapter(onItemClicked : (LanguageViewItem) -> Unit) : AsyncListDifferDelegationAdapter<LanguageViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createSettingsDelegate(onItemClicked)
    )
)

private fun createSettingsDelegate(onItemClicked: (LanguageViewItem) -> Unit) = adapterDelegateViewBinding<LanguageViewItem, HolderLanguageBinding>(
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
