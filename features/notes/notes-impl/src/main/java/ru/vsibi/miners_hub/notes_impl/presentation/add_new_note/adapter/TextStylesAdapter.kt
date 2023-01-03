/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.notes_impl.presentation.add_new_note.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.notes_impl.databinding.HolderTextStyleBinding
import ru.vsibi.miners_hub.notes_impl.presentation.add_new_note.model.TextStyleViewItem
import ru.vsibi.miners_hub.util.AdapterUtil
import ru.vsibi.miners_hub.util.adapterDelegateViewBinding
import ru.vsibi.miners_hub.util.bindWithBinding

class TextStylesAdapter : AsyncListDifferDelegationAdapter<TextStyleViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createSearchDeviceDelegate()
    )
)

fun createSearchDeviceDelegate() =
    adapterDelegateViewBinding<TextStyleViewItem,
            HolderTextStyleBinding>(
        HolderTextStyleBinding::inflate,
    ) {
        bindWithBinding {
            textStyle.setImageResource(item.iconRes)
        }
    }

