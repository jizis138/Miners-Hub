package ru.vsibi.btc_mathematic.knowledge_impl.data.storage.converters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_api.model.Price
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.SchemaTable

class ListMinerConverter {

    @TypeConverter
    fun fromString(string: String): List<Miner> = Json.decodeFromString(string)

    @TypeConverter
    fun toString(list: List<Miner>): String = Json.encodeToString(list)
}