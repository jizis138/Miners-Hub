package ru.vsibi.btc_mathematic.network.tokens

import kotlinx.coroutines.withContext
import ru.vsibi.btc_mathematic.core.coroutines.DispatchersProvider
import ru.vsibi.btc_mathematic.data.KeyValueStorage
import ru.vsibi.btc_mathematic.data.stored

/**
 * Временное решение, предполагается не сохранять access токен, о получать его при входе в приложение через refresh
 */
class AccessTokenStorage(
    persistentStorageFactory: KeyValueStorage.Factory,
) {
    private val preferencesStorage = persistentStorageFactory.create("tokens_persistent.prefs")

    private var accessToken: String? by preferencesStorage.stored("KEY_ACCESS_TOKEN")

    suspend fun getAccessToken(): AccessToken? =
        withContext(DispatchersProvider.IO) {
            return@withContext accessToken?.let(::AccessToken)
        }

    suspend fun setAccessToken(token: AccessToken) =
        withContext(DispatchersProvider.IO) {
            accessToken = token.s
        }

    suspend fun clearAccessToken() {
        withContext(DispatchersProvider.IO) {
            accessToken = null
        }
    }
}