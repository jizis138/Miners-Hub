package ru.vsibi.btc_mathematic.knowledge_impl.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vsibi.btc_mathematic.data.db.converter.LocalDateConverter
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.dao.MinerDao
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.MinerTable

@Database(
    entities = [
        MinerTable::class
    ],
    version = 4
)
@TypeConverters(
    LocalDateConverter::class,
    ListConverter::class,
)
abstract class MinerDatabase : RoomDatabase() {
    abstract val minerDao : MinerDao
}