package ru.vsibi.miners_hub.knowledge_impl.data.storage.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.vsibi.miners_hub.knowledge_impl.data.storage.model.MinerTable

@Dao
interface MinerDao {

    @Query("SELECT * FROM MinerTable")
    fun getMinersFlow(): Flow<List<MinerTable>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMiners(miners : List<MinerTable>)

    @Query("DELETE FROM MinerTable")
    suspend fun clear()
}