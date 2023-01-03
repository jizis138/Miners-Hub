/**
 * Created by Dmitry Popov on 02.10.2022.
 */
package ru.vsibi.miners_hub.notes_impl.domain.repo

import kotlinx.coroutines.flow.Flow
import ru.vsibi.miners_hub.notes_impl.domain.entity.Note

interface NotesRepo {

//    suspend fun refreshProfile(): CallResult<Profile>
//    suspend fun updateProfile(editingProfile: EditingProfile): CallResult<Unit>

    fun observeNotes(): Flow<List<Note>?>

    suspend fun addNote(note: Note)

    suspend fun refreshNotes()

}