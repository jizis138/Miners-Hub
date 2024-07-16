/**
 * Created by Dmitry Popov on 21.04.2024.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.data.service.model.via_btc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ViaBtcCalcResponse(

    @SerialName("code")
    val code : Int,

    @SerialName("data")
    val data : Data,

) {

    @Serializable
    class Data(

        @SerialName("profit")
        val profit : Profit
    )

    @Serializable
    class Profit(
        @SerialName("BTC")
        val BTC : String,
        @SerialName("CNY")
        val CNY : String,
        @SerialName("USD")
        val USD : String,
    )
}