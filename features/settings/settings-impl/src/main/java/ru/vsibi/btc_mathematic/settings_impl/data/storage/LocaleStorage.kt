package ru.vsibi.btc_mathematic.settings_impl.data.storage

import kotlinx.coroutines.withContext
import ru.vsibi.btc_mathematic.core.coroutines.DispatchersProvider
import ru.vsibi.btc_mathematic.data.KeyValueStorage
import ru.vsibi.btc_mathematic.data.stored

class LocaleStorage(
    persistentStorageFactory: KeyValueStorage.Factory,
) {
    private val preferencesStorage = persistentStorageFactory.create("locale.prefs")

    private var locale: String? by preferencesStorage.stored("KEY_LOCALE")

    private var currency: String? by preferencesStorage.stored("KEY_CURRENCY")

    suspend fun getLocale(): String? =
        withContext(DispatchersProvider.IO) {
            return@withContext locale
        }

    suspend fun setLocale(locale : String) =
        withContext(DispatchersProvider.IO) {
            this@LocaleStorage.locale = locale
        }

    suspend fun getCurrency(): String? =
        withContext(DispatchersProvider.IO) {
            return@withContext currency
        }

    suspend fun setCurrency(currency : String) =
        withContext(DispatchersProvider.IO) {
            this@LocaleStorage.currency = currency
        }

    suspend fun clearPrefs() = withContext(DispatchersProvider.IO) {
        preferencesStorage.clear()
    }
}