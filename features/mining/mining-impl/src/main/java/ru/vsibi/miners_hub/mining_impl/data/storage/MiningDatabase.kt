package ru.vsibi.miners_hub.mining_impl.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vsibi.miners_hub.data.db.converter.LocalDateConverter
import ru.vsibi.miners_hub.mining_impl.data.storage.converters.ListConverter
import ru.vsibi.miners_hub.mining_impl.data.storage.converters.PriceConverter
import ru.vsibi.miners_hub.mining_impl.data.storage.dao.MiningDao
import ru.vsibi.miners_hub.mining_impl.data.storage.model.FarmTable

@Database(
    entities = [
        FarmTable::class
    ],
    version = 2,
)
@TypeConverters(
    LocalDateConverter::class,
    ListConverter::class,
    PriceConverter::class,
)
abstract class MiningDatabase : RoomDatabase() {
    abstract val miningDao : MiningDao
}