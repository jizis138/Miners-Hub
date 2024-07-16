/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Miner(
    val id : Long,
    val name: String,
    val schemas: List<Schema>,
    val count : Int,
    val tag : String
) : Parcelable

@Parcelize
@Serializable
data class Schema(
    val algorithmName : String = "SHA-256",
    val hashrate: Long,
    val power: Long,
): Parcelable