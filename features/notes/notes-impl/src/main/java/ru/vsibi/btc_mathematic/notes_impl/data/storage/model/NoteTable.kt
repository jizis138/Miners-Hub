package ru.vsibi.btc_mathematic.notes_impl.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NoteTable(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val description: String
)