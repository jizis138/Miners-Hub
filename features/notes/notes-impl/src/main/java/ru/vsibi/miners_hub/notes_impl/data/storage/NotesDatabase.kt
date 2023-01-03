package ru.vsibi.miners_hub.notes_impl.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vsibi.miners_hub.data.db.converter.LocalDateConverter
import ru.vsibi.miners_hub.notes_impl.data.storage.dao.NotesDao
import ru.vsibi.miners_hub.notes_impl.data.storage.model.NoteTable

@Database(
    entities = [
        NoteTable::class
    ],
    version = 2,
)
@TypeConverters(
    LocalDateConverter::class
)
abstract class NotesDatabase : RoomDatabase() {
    abstract val notesDao: NotesDao
}