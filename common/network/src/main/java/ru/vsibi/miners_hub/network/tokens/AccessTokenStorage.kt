package ru.vsibi.miners_hub.network.tokens

import kotlinx.coroutines.withContext
import ru.vsibi.miners_hub.core.coroutines.DispatchersProvider
import ru.vsibi.miners_hub.data.KeyValueStorage
import ru.vsibi.miners_hub.data.stored

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