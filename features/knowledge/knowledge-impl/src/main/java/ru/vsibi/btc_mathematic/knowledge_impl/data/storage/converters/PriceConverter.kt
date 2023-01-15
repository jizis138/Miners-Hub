package ru.vsibi.btc_mathematic.knowledge_impl.data.storage.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vsibi.btc_mathematic.knowledge_api.model.Price
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.SchemaTable

class PriceConverter {

    @TypeConverter
    fun fromString(string: String): Price = Json.decodeFromString(string)

    @TypeConverter
    fun toString(price : Price): String = Json.encodeToString(price)
}