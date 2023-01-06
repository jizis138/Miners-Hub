package ru.vsibi.miners_hub.mining_impl.data.storage.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vsibi.miners_hub.mining_impl.data.storage.model.MinerDB

class ListConverter {
    @TypeConverter
    fun fromString(string: String): List<MinerDB> = Json.decodeFromString(string)

    @TypeConverter
    fun toString(list: List<MinerDB>): String = Json.encodeToString(list)
}