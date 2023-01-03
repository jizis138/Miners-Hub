package ru.vsibi.miners_hub.notes_impl.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.vsibi.miners_hub.notes_impl.data.storage.model.NoteTable

@Dao
interface NotesDao {

    @Query("SELECT * FROM NoteTable")
    fun getNotesFlow(): Flow<List<NoteTable>?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(noteTable: NoteTable)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun saveDocuments(documentTables: List<DocumentTable>)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun saveProtectionScaleSectors(sectors: List<ProtectionScaleSectorTable>)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun saveProtectionExistingPolicies(existingPolicies: List<ProtectionScaleExistingPolicyTable>)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun saveProtectionRemainingPolicies(remainingPolicies: List<ProtectionScaleRemainingPolicyTable>)
//
//    @Query("DELETE FROM ProfileTable WHERE id = :id")
//    suspend fun removeProfile(id: String)
//
//    @Query("DELETE FROM DocumentTable WHERE profileId = :profileId")
//    suspend fun removeDocuments(profileId: String)
//
//    @Query("DELETE FROM ProtectionScaleSectorTable WHERE profileId = :profileId")
//    suspend fun removeProtectionScaleSectors(profileId: String)
//
//    @Query("DELETE FROM ProtectionScaleExistingPolicyTable WHERE profileId = :profileId")
//    suspend fun removeProtectionScaleExistingPolicies(profileId: String)
//
//    @Query("DELETE FROM ProtectionScaleRemainingPolicyTable WHERE profileId = :profileId")
//    suspend fun removeProtectionScaleRemainingPolicies(profileId: String)
//
//    @Query("SELECT * FROM ProfileTable WHERE id = :id")
//    fun getProfileFlow(id: String): Flow<ProfileResult?>
}