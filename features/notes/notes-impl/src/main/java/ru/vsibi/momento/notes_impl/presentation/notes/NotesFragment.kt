/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.notes_impl.presentation.notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.vsibi.momento.notes_impl.R
import ru.vsibi.momento.notes_impl.databinding.FragmentNotesBinding
import ru.vsibi.momento.notes_impl.presentation.notes.adapter.NotesAdapter
import ru.vsibi.momento.notes_impl.presentation.notes.model.NoteViewItem
import ru.vsibi.momento.presentation.base.ui.BaseFragment
import ru.vsibi.momento.presentation.base.util.fragmentViewBinding
import ru.vsibi.momento.presentation.base.util.noEventsExpected
import ru.vsibi.momento.presentation.base.util.viewModel
import ru.vsibi.momento.util.collectLatestWhenStarted
import ru.vsibi.momento.util.onClick

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