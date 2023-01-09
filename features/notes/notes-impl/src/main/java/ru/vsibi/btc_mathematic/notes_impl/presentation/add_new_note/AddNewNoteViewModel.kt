/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.notes_impl.presentation.add_new_note

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.notes_impl.domain.logic.NotesInteractor


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

    fun onAddNewNoteClicked(description: String) {
        if(description.isEmpty()) return
        viewModelScope.launch {
            notesInteractor.addNewNote("", description)
            exitWithoutResult()
        }
    }

    override fun onBackPressed(): Boolean {
        exitWithoutResult()
        return true
    }

}