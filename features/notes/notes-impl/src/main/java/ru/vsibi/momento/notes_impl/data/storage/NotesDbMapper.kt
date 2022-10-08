package ru.vsibi.momento.notes_impl.data.storage

import ru.vsibi.momento.notes_impl.data.storage.model.NoteTable
import ru.vsibi.momento.notes_impl.domain.entity.Note

object NotesDbMapper {

    fun mapNotes(notes: List<NoteTable>) = notes.map { noteTable ->
        Note(
            id = noteTable.id,
            title = noteTable.title,
            description = noteTable.description
        )
    }

    fun mapTableNote(note: Note) = NoteTable(id = note.id, title = note.title, description = note.description)
}