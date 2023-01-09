/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.data.service.model.miner_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Schema (
    @SerialName("speed")
    val speed : Long,
    @SerialName("power")
    val power : Long,
)