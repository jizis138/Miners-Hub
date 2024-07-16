/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.settings_api

import android.os.Parcelable
import kotlinx.coroutines.flow.Flow
import kotlinx.parcelize.Parcelize
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContractApi
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.navigation.model.NoResult

interface SettingsFeature {

    val currencyNavigationContract: NavigationContractApi<NoParams, CurrencyResult>

    val navigationContract: NavigationContractApi<NoParams, NoResult>

    val localeFlow : Flow<LocaleEvent>

    suspend fun getSavedLocale() : String

    suspend fun getSavedCurrency(): String

    sealed class LocaleEvent : Parcelable {

        @Parcelize
        class LocaleChanged(val locale : String) : LocaleEvent()
    }

    sealed class CurrencyResult : Parcelable {

        @Parcelize
        data class CurrencyChanged(val currency: String) : CurrencyResult(), Parcelable

        @Parcelize
        object Cancel : CurrencyResult(), Parcelable
    }
}