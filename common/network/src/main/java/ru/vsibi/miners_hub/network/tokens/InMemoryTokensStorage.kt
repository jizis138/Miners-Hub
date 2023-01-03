package ru.vsibi.miners_hub.network.tokens

import ru.vsibi.miners_hub.network.tokens.AccessToken

class InMemoryTokensStorage {
    private var inMemoryAccessToken: AccessToken? = null

    fun saveAccessToken(accessToken: AccessToken) {
        inMemoryAccessToken = accessToken
    }

    fun getAccessToken(): AccessToken? = inMemoryAccessToken

    fun clear() {
        inMemoryAccessToken = null
    }
}