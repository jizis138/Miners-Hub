/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.notes_impl.presentation.notes

import android.os.Bundle
import android.view.View
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.vsibi.miners_hub.notes_impl.R
import ru.vsibi.miners_hub.notes_impl.databinding.FragmentNotesBinding
import ru.vsibi.miners_hub.notes_impl.presentation.notes.adapter.NotesAdapter
import ru.vsibi.miners_hub.notes_impl.presentation.notes.model.NoteViewItem
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.fragmentViewBinding
import ru.vsibi.miners_hub.presentation.base.util.noEventsExpected
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.util.collectLatestWhenStarted
import ru.vsibi.miners_hub.util.onClick

class NotesFragment : BaseFragment<NotesState, NotesEvent>(R.layout.fragment_notes) {

    private val binding by fragmentViewBinding(FragmentNotesBinding::bind)

    private val adapter = NotesAdapter()

    override val vm: NotesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = adapter

        addNewNote.onClick {
            vm.onAddNoteClicked()
        }

        vm.items
            .distinctUntilChanged()
            .collectLatestWhenStarted(this@NotesFragment) { items ->
                items?.let {
                    adapter.items = items.map {
                        NoteViewItem(it.description)
                    }
                }
            }
    }

    override fun onResume() {
        super.onResume()
        vm.refreshNotes()
    }

    override fun onUpdateState(state: NotesState) {

    }

    override fun onRecieveEvent(event: NotesEvent) = noEventsExpected()
}