package ru.vsibi.btc_mathematic.knowledge_impl.data.service

import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive
import ru.vsibi.btc_mathematic.core.exceptions.WrongServerResponseException
import ru.vsibi.btc_mathematic.knowledge_api.model.CalculationState
import ru.vsibi.btc_mathematic.knowledge_impl.data.service.model.DifficultyResponse
import ru.vsibi.btc_mathematic.knowledge_api.model.Difficulty
import ru.vsibi.btc_mathematic.knowledge_impl.data.service.model.via_btc.ViaBtcCalcRequest
import ru.vsibi.btc_mathematic.knowledge_impl.data.service.model.via_btc.ViaBtcCalcResponse
import ru.vsibi.btc_mathematic.network.client.NetworkClientFactory
import java.time.LocalDateTime
import kotlin.math.roundToInt

class ViaBtcService(
    clientFactory: NetworkClientFactory,
) {

    private val client = clientFactory.create()

    suspend fun fetchIncomePerDay(
        coin: String,
        coinPrice: String,
        difficulty: String,
        hashrate: String
    ): Double {
        val body = ViaBtcCalcRequest(
            coin = coin,
            coinPrice = coinPrice,
            currency = "USD",
            diff = difficulty,
            ppsFeeRate = "0.0000",
            hashrate = hashrate,
            hashUnit = "TH/s"
        )

        val response = client.runPost<ViaBtcCalcRequest, ViaBtcCalcResponse>(
            path = "https://www.viabtc.com/res/tools/calculator",
            useBaseUrl = false,
            body = body
        )

        return response.data.profit.BTC.toDouble()
    }
}