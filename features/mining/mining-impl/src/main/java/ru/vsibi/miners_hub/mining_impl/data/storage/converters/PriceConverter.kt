package ru.vsibi.miners_hub.mining_impl.data.storage.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vsibi.miners_hub.mining_impl.data.storage.model.MinerDB
import ru.vsibi.miners_hub.mining_impl.data.storage.model.PriceDB

class PriceConverter {
    @TypeConverter
    fun fromString(string: String): PriceDB = Json.decodeFromString(string)

    @TypeConverter
    fun toString(list: PriceDB): String = Json.encodeToString(list)
}