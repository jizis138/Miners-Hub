/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.notes_impl.presentation.notes

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vsibi.momento.mvi.BaseViewModel
import ru.vsibi.momento.navigation.RootRouter
import ru.vsibi.momento.navigation.model.RequestParams
import ru.vsibi.momento.notes_impl.domain.logic.NotesInteractor
import ru.vsibi.momento.notes_impl.presentation.add_new_note.AddNewNoteNavigationContract
import ru.vsibi.momento.notes_impl.presentation.notes.model.NoteViewItem

class NotesViewModel(
    router: RootRouter,
    requestParams: RequestParams,
    private val notesInteractor: NotesInteractor
) : BaseViewModel<NotesState, NotesEvent>(
    router, requestParams
) {

    private val addNewNoteLauncher = launcher(AddNewNoteNavigationContract)

    val items = notesInteractor.observeNotes()

    override fun firstState(): NotesState {
        return NotesState(listOf())
    }

    fun onAddNoteClicked() {
        addNewNoteLauncher.launch()
    }

    fun refreshNotes() {
        viewModelScope.launch {
            notesInteractor.refreshNotes()
        }
    }

}