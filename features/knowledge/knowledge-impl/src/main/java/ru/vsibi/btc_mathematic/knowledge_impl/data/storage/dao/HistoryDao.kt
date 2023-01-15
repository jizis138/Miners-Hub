/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.HistoryTable
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model.MinerTable

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryTable")
    suspend fun getHistoryItems(): List<HistoryTable>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHistory(historyTable: HistoryTable)

    @Query("DELETE FROM HistoryTable WHERE id = :id")
    suspend fun removeHistory(id: Long)

    @Query("DELETE FROM HistoryTable")
    suspend fun removeHistoryTable()
}