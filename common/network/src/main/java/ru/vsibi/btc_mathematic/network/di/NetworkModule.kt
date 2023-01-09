package ru.vsibi.btc_mathematic.network.di

import ru.vsibi.btc_mathematic.network.tokens.RefreshTokenStorage
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.logging.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vsibi.btc_mathematic.core.environment.Environment
import ru.vsibi.btc_mathematic.data.di.qualifier.StorageQualifier
import ru.vsibi.btc_mathematic.di.bindSafe
import ru.vsibi.btc_mathematic.di.factory
import ru.vsibi.btc_mathematic.di.single
import ru.vsibi.btc_mathematic.network.client.JwtNetworkClientFactory
import ru.vsibi.btc_mathematic.network.client.NetworkClientFactory
import ru.vsibi.btc_mathematic.network.client.SimpleNetworkClientFactory
import ru.vsibi.btc_mathematic.network.tokens.*
import ru.vsibi.btc_mathematic.network.util.NetworkController
import ru.vsibi.btc_mathematic.network.util.NetworkLogger

object NetworkModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
    )

    private fun createFeatureModule() = module {
        single(
            definition = {
                JwtNetworkClientFactory(
                    httpClientEngine = get(),
                    environment = get(),
                    baseUrl = get<Environment>().endpoints.apiBaseUrl,
                    networkLogger = get(),
                    tokensRepo = get(),
                    forceLogoutManager = lazy { get() },
                )
            },
            qualifier = named(NetworkClientFactoryQualifier.Authorized)
        ) bindSafe NetworkClientFactory::class

        single(
            definition = {
                SimpleNetworkClientFactory(
                    httpClientEngine = get(),
                    environment = get(),
                    baseUrl = get<Environment>().endpoints.refreshTokenUrl,
                    networkLogger = get(),
                    forceLogoutManager = lazy { get() }
                )
            },
            qualifier = named(NetworkClientFactoryQualifier.RefreshToken)
        ) bindSafe NetworkClientFactory::class

        single(
            definition = {
                SimpleNetworkClientFactory(
                    httpClientEngine = get(),
                    environment = get(),
                    baseUrl = get<Environment>().endpoints.apiBaseUrl,
                    networkLogger = get(),
                    forceLogoutManager = lazy { get() },
                )
            },
            qualifier = named(NetworkClientFactoryQualifier.Unauthorized)
        ) bindSafe NetworkClientFactory::class

        single {
            OkHttp.create()
        } bindSafe HttpClientEngine::class

        single(::InMemoryTokensStorage)
        //TODO использовать StorageQualifier.Secure
        single { RefreshTokenStorage(get(named(StorageQualifier.Simple))) }
        single { AccessTokenStorage(get(named(StorageQualifier.Simple))) }

        single(::TokensRepoImpl) bindSafe TokensRepo::class

        single { TokensService(get(named(NetworkClientFactoryQualifier.RefreshToken))) }

        factory(::NetworkController)

        single(::NetworkLogger) bindSafe Logger::class

    }
}