package ru.vsibi.btc_mathematic.mining_impl.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vsibi.btc_mathematic.data.db.converter.LocalDateConverter
import ru.vsibi.btc_mathematic.mining_impl.data.storage.converters.ListConverter
import ru.vsibi.btc_mathematic.mining_impl.data.storage.converters.PriceConverter
import ru.vsibi.btc_mathematic.mining_impl.data.storage.dao.MiningDao
import ru.vsibi.btc_mathematic.mining_impl.data.storage.model.FarmTable

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