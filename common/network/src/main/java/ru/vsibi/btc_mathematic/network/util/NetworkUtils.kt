package ru.vsibi.btc_mathematic.network.util

import ru.vsibi.btc_mathematic.network.model.BaseResponse
import ru.vsibi.btc_mathematic.network.tokens.AccessToken
import io.ktor.client.request.*
import io.ktor.http.*
import ru.vsibi.btc_mathematic.core.exceptions.WrongServerResponseException

object NetworkUtils {
    private const val BEARER = "Bearer"

    fun buildAuthHeader(accessToken: AccessToken) = "$BEARER ${accessToken.s}"
}

val HttpRequestData.authHeader: String? get() = this.headers[HttpHeaders.Authorization]

fun <T> BaseResponse<T>.requireData(): T = when {
    error != null -> throw WrongServerResponseException()
    else -> data!!
}
