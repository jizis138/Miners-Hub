/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.notes_impl.presentation.add_new_note.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.notes_impl.databinding.HolderTextStyleBinding
import ru.vsibi.btc_mathematic.notes_impl.presentation.add_new_note.model.TextStyleViewItem
import ru.vsibi.btc_mathematic.util.AdapterUtil
import ru.vsibi.btc_mathematic.util.adapterDelegateViewBinding
import ru.vsibi.btc_mathematic.util.bindWithBinding

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

