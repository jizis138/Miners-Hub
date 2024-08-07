package ru.vsibi.btc_mathematic.knowledge_impl.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
class MinerTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val schemas: List<SchemaTable>,
    val count : Int,
    val tag : String
)

@Serializable
data class SchemaTable(
    val algorithmName: String,
    val hashrate: Long,
    val power: Long
)