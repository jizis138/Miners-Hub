/**
 * Created by Dmitry Popov on 21.04.2024.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.data.service.model.via_btc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ViaBtcCalcRequest(

    @SerialName("coin")
    val coin : String,

    @SerialName("coin_price")
    val coinPrice : String,

    @SerialName("currency")
    val currency : String,

    @SerialName("diff")
    val diff : String,

    @SerialName("pps_fee_rate")
    val ppsFeeRate : String,

    @SerialName("hashrate")
    val hashrate : String,

    @SerialName("hash_unit")
    val hashUnit : String,
)