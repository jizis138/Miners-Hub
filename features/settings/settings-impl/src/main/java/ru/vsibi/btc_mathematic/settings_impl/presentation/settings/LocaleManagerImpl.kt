/**
 * Created by Dmitry Popov on 10.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.settings

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.vsibi.btc_mathematic.settings_api.SettingsFeature
import ru.vsibi.btc_mathematic.settings_impl.data.storage.LocaleStorage
import ru.vsibi.btc_mathematic.settings_impl.domain.logic.LocaleManager

class LocaleManagerImpl(
    private val localeStorage: LocaleStorage
) : LocaleManager {

    private val localeChannel = Channel<SettingsFeature.LocaleEvent>(onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override suspend fun changeLocale(locale: String) {
        localeStorage.setLocale(locale)
        val localeEvent = SettingsFeature.LocaleEvent.LocaleChanged(locale)
        localeChannel.trySend(localeEvent)
    }

    override fun observeLocale() = localeChannel.receiveAsFlow()

    override suspend fun getSavedLocale(): String = localeStorage.getLocale() ?: "en"

    override suspend fun changeCurrency(currency: String) = localeStorage.setCurrency(currency)

    override suspend fun getSavedCurrency(): String = localeStorage.getCurrency() ?: run {
        if (getSavedLocale() == "en") {
            "USD"
        } else {
            "RUB"
        }
    }

}