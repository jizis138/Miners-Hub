package ru.vsibi.btc_mathematic.network.client

import ru.vsibi.btc_mathematic.network.tokens.TokenUpdateException
import ru.vsibi.btc_mathematic.network.tokens.TokensRepo
import ru.vsibi.btc_mathematic.network.util.NetworkUtils
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.vsibi.btc_mathematic.core.environment.Environment
import ru.vsibi.btc_mathematic.core.exceptions.UnauthorizedException

internal class JwtNetworkClientFactory(
    private val httpClientEngine: HttpClientEngine,
    private val environment: Environment,
    private val baseUrl: String,
    private val networkLogger: Logger,
    private val tokensRepo: TokensRepo,
    //Циклическая зависимость. LogoutManager зависит внутри от ForceLogoutManager через
    //JwtNetworkClientFactory, тк по факту они не зависят, решается lazy инициализацией
    private val forceLogoutManager: Lazy<ForceLogoutManager>,
) : NetworkClientFactory {

    override fun create(
        adjustClientBlock: HttpClientConfig<*>.() -> Unit,
    ): NetworkClient = SimpleNetworkClientFactory(
        httpClientEngine = httpClientEngine,
        environment = environment,
        baseUrl = baseUrl,
        networkLogger = networkLogger,
        forceLogoutManager = forceLogoutManager
    ).create {

        defaultRequestSuspend {
            val accessToken = tokensRepo.getAccessToken()
            headers.remove(HttpHeaders.Authorization)
            header(HttpHeaders.Authorization, NetworkUtils.buildAuthHeader(accessToken))
        }

        install(NeedRetry) {
            retryCondition { _: HttpRequest, response: HttpResponse ->
                if (response.status.value == HttpStatusCode.Unauthorized.value) {
                    tokensRepo.updateToken()
                    true
                } else {
                    false
                }
            }
        }

        HttpResponseValidator {
            handleResponseException { cause: Throwable ->
                when (cause) {
                    is TokenUpdateException, is UnauthorizedException -> forceLogoutManager.value.forceLogout()
                }
            }
        }

        adjustClientBlock()
    }
}