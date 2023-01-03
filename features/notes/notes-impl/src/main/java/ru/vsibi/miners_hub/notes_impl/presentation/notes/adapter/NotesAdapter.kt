/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.notes_impl.presentation.notes.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.notes_impl.databinding.HolderNoteBinding
import ru.vsibi.miners_hub.notes_impl.presentation.notes.model.NoteViewItem
import ru.vsibi.miners_hub.util.AdapterUtil
import ru.vsibi.miners_hub.util.adapterDelegateViewBinding
import ru.vsibi.miners_hub.util.bindWithBinding

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

