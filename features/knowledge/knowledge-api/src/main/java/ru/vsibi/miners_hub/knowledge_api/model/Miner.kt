/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Miner(
    val id : Long,
    val name: String,
    val schemas: List<Schema>,
    val count : Int,
    val tag : String
) : Parcelable

@Parcelize
data class Schema(
    val algorithmName : String = "SHA-256",
    val hashrate: Long,
    val power: Long,
): Parcelable