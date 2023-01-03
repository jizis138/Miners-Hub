package ru.vsibi.miners_hub.network.tokens

import kotlinx.coroutines.withContext
import ru.vsibi.miners_hub.core.coroutines.DispatchersProvider
import ru.vsibi.miners_hub.data.KeyValueStorage
import ru.vsibi.miners_hub.data.stored

class RefreshTokenStorage(
    persistentStorageFactory: KeyValueStorage.Factory,
) {
    private val preferencesStorage = persistentStorageFactory.create("tokens_persistent.prefs")

    private var refreshToken: String? by preferencesStorage.stored("KEY_REFRESH_TOKEN")

    suspend fun getRefreshToken(): RefreshToken? =
        withContext(DispatchersProvider.IO) {
            return@withContext refreshToken?.let(::RefreshToken)
        }

    suspend fun setRefreshToken(token: RefreshToken) =
        withContext(DispatchersProvider.IO) {
            refreshToken = token.s
        }

    suspend fun clearRefreshToken() {
        withContext(DispatchersProvider.IO) {
            refreshToken = null
        }
    }
}