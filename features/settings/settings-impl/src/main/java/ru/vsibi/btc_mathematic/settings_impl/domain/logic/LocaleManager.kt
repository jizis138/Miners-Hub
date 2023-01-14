/**
 * Created by Dmitry Popov on 10.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.domain.logic

import kotlinx.coroutines.flow.Flow
import ru.vsibi.btc_mathematic.settings_api.SettingsFeature

interface LocaleManager {

    suspend fun changeLocale(locale: String)

    fun observeLocale(): Flow<SettingsFeature.LocaleEvent>

    suspend fun getSavedLocale(): String

    suspend fun changeCurrency(currency : String)

    suspend fun getSavedCurrency() : String

}