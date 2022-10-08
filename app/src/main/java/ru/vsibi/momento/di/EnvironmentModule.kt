package ru.vsibi.momento.di

import org.koin.dsl.module
import ru.vsibi.momento.BuildConfig
import ru.vsibi.momento.core.environment.Endpoints
import ru.vsibi.momento.core.environment.Environment


object EnvironmentModule {
    operator fun invoke() = module {
        single {

            val apiBaseUrl = BuildConfig.MAIN_API_BASE_URL_DEV
            val refreshTokenUrl = BuildConfig.REFRESH_TOKEN_URL_DEV

            Environment(
                endpoints = Endpoints(
                    apiBaseUrl = apiBaseUrl,
                    refreshTokenUrl = refreshTokenUrl,
                ),
                isDebug = BuildConfig.IS_DEBUG,
            )
        }
    }
}