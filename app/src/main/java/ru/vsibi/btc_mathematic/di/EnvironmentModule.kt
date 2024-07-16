package ru.vsibi.btc_mathematic.di

import org.koin.dsl.module
import ru.vsibi.btc_mathematic.BuildConfig
import ru.vsibi.btc_mathematic.core.environment.Endpoints
import ru.vsibi.btc_mathematic.core.environment.Environment


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
                appVersion = BuildConfig.VERSION_NAME,
                buildDate = BuildConfig.BUILD_DATE
            )
        }
    }
}