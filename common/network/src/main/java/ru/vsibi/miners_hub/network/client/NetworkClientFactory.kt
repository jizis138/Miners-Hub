package ru.vsibi.miners_hub.network.client

import io.ktor.client.*
import ru.vsibi.miners_hub.network.client.NetworkClient

interface NetworkClientFactory {
    fun create(
        adjustClientBlock: HttpClientConfig<*>.() -> Unit = {},
    ): NetworkClient
}