package ru.vsibi.btc_mathematic.network.tokens

import kotlinx.coroutines.withContext
import ru.vsibi.btc_mathematic.core.coroutines.DispatchersProvider
import ru.vsibi.btc_mathematic.data.KeyValueStorage
import ru.vsibi.btc_mathematic.data.stored

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