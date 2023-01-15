package ru.vsibi.btc_mathematic.knowledge_impl.data.storage.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.SchemaTable

class ListSchemaConverter {
    @TypeConverter
    fun fromString(string: String): List<SchemaTable> = Json.decodeFromString(string)

    @TypeConverter
    fun toString(list: List<SchemaTable>): String = Json.encodeToString(list)
}