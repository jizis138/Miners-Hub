/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.notes_impl.presentation.notes.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.notes_impl.databinding.HolderNoteBinding
import ru.vsibi.btc_mathematic.notes_impl.presentation.notes.model.NoteViewItem
import ru.vsibi.btc_mathematic.util.AdapterUtil
import ru.vsibi.btc_mathematic.util.adapterDelegateViewBinding
import ru.vsibi.btc_mathematic.util.bindWithBinding

class NotesAdapter : AsyncListDifferDelegationAdapter<NoteViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createSearchDeviceDelegate()
    )
)

fun createSearchDeviceDelegate() =
    adapterDelegateViewBinding<NoteViewItem,
            HolderNoteBinding>(
        HolderNoteBinding::inflate,
    ) {
        bindWithBinding {
            noteTitle.text = item.text
        }
    }

