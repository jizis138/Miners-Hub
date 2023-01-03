/**
 * Created by Dmitry Popov on 02.10.2022.
 */
package ru.vsibi.miners_hub.notes_impl.domain.logic

import kotlinx.coroutines.flow.Flow
import ru.vsibi.miners_hub.notes_impl.domain.entity.Note
import ru.vsibi.miners_hub.notes_impl.domain.repo.NotesRepo

class NotesInteractor (private val notesRepo: NotesRepo) {

    suspend fun addNewNote(title : String, description : String) {
        notesRepo.addNote(Note(0, title = title, description = description))
    }

    fun observeNotes(): Flow<List<Note>?> = notesRepo.observeNotes()

    suspend fun refreshNotes() = notesRepo.refreshNotes()
}