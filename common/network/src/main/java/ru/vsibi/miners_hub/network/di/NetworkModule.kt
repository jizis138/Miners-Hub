package ru.vsibi.miners_hub.network.di

import ru.vsibi.miners_hub.network.tokens.RefreshTokenStorage
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.logging.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vsibi.miners_hub.core.environment.Environment
import ru.vsibi.miners_hub.data.di.qualifier.StorageQualifier
import ru.vsibi.miners_hub.di.bindSafe
import ru.vsibi.miners_hub.di.factory
import ru.vsibi.miners_hub.di.single
import ru.vsibi.miners_hub.network.client.JwtNetworkClientFactory
import ru.vsibi.miners_hub.network.client.NetworkClientFactory
import ru.vsibi.miners_hub.network.client.SimpleNetworkClientFactory
import ru.vsibi.miners_hub.network.tokens.*
import ru.vsibi.miners_hub.network.util.NetworkController
import ru.vsibi.miners_hub.network.util.NetworkLogger

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