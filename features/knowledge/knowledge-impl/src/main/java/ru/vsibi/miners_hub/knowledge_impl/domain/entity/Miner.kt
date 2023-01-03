/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Miner(
    val id : Long,
    val name: String,
    val schemas: List<Schema>
) : Parcelable

@Parcelize
data class Schema(
    val algorithmName : String? = null,
    val hashrate: Long,
    val power: Long
): Parcelable