package ru.vsibi.btc_mathematic.notes_impl.data.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vsibi.btc_mathematic.data.db.DatabaseFactory
import ru.vsibi.btc_mathematic.notes_impl.domain.entity.Note

class NotesStorage(
    databaseFactory: DatabaseFactory,
) {

    private val db by databaseFactory.create(NotesDatabase::class, "NotesDatabase")

    fun getNotesFlow(): Flow<List<Note>?> = db.notesDao
        .getNotesFlow()
        .map { notesResult -> notesResult?.let(NotesDbMapper::mapNotes) }

    suspend fun addNote(note: Note) = db.notesDao.addNote(note.let(NotesDbMapper::mapTableNote))
}