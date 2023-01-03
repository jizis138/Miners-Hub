/**
 * Created by Dmitry Popov on 02.10.2022.
 */
package ru.vsibi.miners_hub.notes_impl.data.repo_impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import ru.vsibi.miners_hub.core.coroutines.DispatchersProvider
import ru.vsibi.miners_hub.notes_impl.data.storage.NotesStorage
import ru.vsibi.miners_hub.notes_impl.domain.entity.Note
import ru.vsibi.miners_hub.notes_impl.domain.repo.NotesRepo

class NotesRepoImpl(private val notesStorage: NotesStorage) : NotesRepo {

    private val notesSharedFlow: SharedFlow<List<Note>?> = notesStorage.getNotesFlow()
        .shareIn(scope = CoroutineScope(DispatchersProvider.IO), started = SharingStarted.Eagerly, replay = 1)

    override fun observeNotes(): Flow<List<Note>?> = notesSharedFlow

    override suspend fun addNote(note: Note) {
        notesStorage.addNote(note)
    }

    override suspend fun refreshNotes() {

    }
}