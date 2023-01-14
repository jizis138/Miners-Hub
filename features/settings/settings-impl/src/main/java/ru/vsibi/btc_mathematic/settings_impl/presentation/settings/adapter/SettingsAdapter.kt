/**
 * Created by Dmitry Popov on 14.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.settings.adapter

import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.settings_impl.databinding.HolderSettingsBinding
import ru.vsibi.btc_mathematic.settings_impl.presentation.settings.model.SettingsViewItem
import ru.vsibi.btc_mathematic.util.*

class SettingsAdapter() : AsyncListDifferDelegationAdapter<SettingsViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createSettingsDelegate()
    )
)

private fun createSettingsDelegate() = adapterDelegateViewBinding<SettingsViewItem, HolderSettingsBinding>(
    HolderSettingsBinding::inflate
) {

    bindWithBinding {
        icon.setImageResource(item.icon)
        title.setPrintableText(item.title)
        lock.isVisible = item.isLocked
        root.onClick {
            item.onItemClicked()
        }
    }
}
