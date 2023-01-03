package ru.vsibi.miners_hub.network.tokens

import ru.vsibi.miners_hub.network.client.NetworkClientFactory
import ru.vsibi.miners_hub.network.model.BaseResponse
import ru.vsibi.miners_hub.network.model.RefreshTokenResponse
import ru.vsibi.miners_hub.network.util.requireData

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