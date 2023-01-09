package ru.vsibi.btc_mathematic.notes_impl.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vsibi.btc_mathematic.data.db.converter.LocalDateConverter
import ru.vsibi.btc_mathematic.notes_impl.data.storage.dao.NotesDao
import ru.vsibi.btc_mathematic.notes_impl.data.storage.model.NoteTable

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