package ru.vsibi.btc_mathematic.network.client

import io.ktor.client.*

interface NetworkClientFactory {
    fun create(
        adjustClientBlock: HttpClientConfig<*>.() -> Unit = {},
    ): NetworkClient
}