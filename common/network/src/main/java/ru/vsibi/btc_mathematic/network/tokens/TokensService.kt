package ru.vsibi.btc_mathematic.network.tokens

import ru.vsibi.btc_mathematic.network.client.NetworkClientFactory
import ru.vsibi.btc_mathematic.network.model.BaseResponse
import ru.vsibi.btc_mathematic.network.model.RefreshTokenResponse
import ru.vsibi.btc_mathematic.network.util.requireData

class TokensService(
    networkClientFactory: NetworkClientFactory,
) {
    private val client = networkClientFactory.create()

    suspend fun callUpdateToken(accessToken: AccessToken, refreshToken: RefreshToken): RefreshTokenResponse {
        val params = mapOf(
            "access_token" to accessToken.s,
            "refresh_token" to refreshToken.s,
        )
        return client.runSubmitForm<BaseResponse<RefreshTokenResponse>>(
            path = "authorization/refresh",
            params = params,
            encodeInQuery = false
        ).requireData()
    }
}