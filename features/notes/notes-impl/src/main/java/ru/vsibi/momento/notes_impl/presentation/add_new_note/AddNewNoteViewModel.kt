/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento.notes_impl.presentation.add_new_note

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vsibi.momento.mvi.BaseViewModel
import ru.vsibi.momento.navigation.RootRouter
import ru.vsibi.momento.navigation.model.RequestParams
import ru.vsibi.momento.notes_impl.domain.logic.NotesInteractor


class AddNewNoteViewModel(
    router: RootRouter, requestParams: RequestParams,
    private val notesInteractor: NotesInteractor
) :
    BaseViewModel<AddNewNoteState, AddNewNoteEvent>(
        router, requestParams
    ) {

    override fun firstState(): AddNewNoteState {
        return AddNewNoteState()
    }

    fun onAddNewNoteClicked(title: String, description: String) {
        if(title.isEmpty() && description.isEmpty()) return
        viewModelScope.launch {
            notesInteractor.addNewNote(title, description)
            exitWithoutResult()
        }
    }

    override fun onBackPressed(): Boolean {
        exitWithoutResult()
        return true
    }

}