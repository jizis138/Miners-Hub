package ru.vsibi.btc_mathematic.knowledge_impl.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vsibi.btc_mathematic.data.db.converter.LocalDateConverter
import ru.vsibi.btc_mathematic.data.db.converter.LocalDateTimeConverter
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.converters.*
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.dao.HistoryDao
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.dao.MinerDao
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.HistoryTable
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.MinerTable

@Database(
    entities = [
        MinerTable::class,
        HistoryTable::class
    ],
    version = 7
)
@TypeConverters(
    LocalDateConverter::class,
    LocalDateTimeConverter::class,
    ListSchemaConverter::class,
    PriceConverter::class,
    ListMinerConverter::class,
    ExchangeRateConverter::class,
    DifficultyConverter::class
)
abstract class MinerDatabase : RoomDatabase() {
    abstract val minerDao: MinerDao
    abstract val historyDao: HistoryDao
}