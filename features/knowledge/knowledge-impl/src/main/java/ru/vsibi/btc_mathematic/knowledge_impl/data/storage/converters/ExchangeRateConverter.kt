package ru.vsibi.btc_mathematic.knowledge_impl.data.storage.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vsibi.btc_mathematic.knowledge_api.model.ExchangeRate
import ru.vsibi.btc_mathematic.knowledge_api.model.Price
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.SchemaTable

class ExchangeRateConverter {

    @TypeConverter
    fun fromString(string: String): ExchangeRate = Json.decodeFromString(string)

    @TypeConverter
    fun toString(exchangeRate : ExchangeRate): String = Json.encodeToString(exchangeRate)
}