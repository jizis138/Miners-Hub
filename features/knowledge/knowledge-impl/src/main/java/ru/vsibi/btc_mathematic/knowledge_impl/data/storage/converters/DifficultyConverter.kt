package ru.vsibi.btc_mathematic.knowledge_impl.data.storage.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vsibi.btc_mathematic.knowledge_api.model.Difficulty
import ru.vsibi.btc_mathematic.knowledge_api.model.ExchangeRate
import ru.vsibi.btc_mathematic.knowledge_api.model.Price
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.SchemaTable

class DifficultyConverter {

    @TypeConverter
    fun fromString(string: String): Difficulty = Json.decodeFromString(string)

    @TypeConverter
    fun toString(difficulty: Difficulty): String = Json.encodeToString(difficulty)
}