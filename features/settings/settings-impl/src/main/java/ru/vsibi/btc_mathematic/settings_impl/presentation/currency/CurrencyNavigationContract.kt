package ru.vsibi.btc_mathematic.settings_impl.presentation.currency

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.settings_api.SettingsFeature

object CurrencyNavigationContract :
    NavigationContract<NoParams, SettingsFeature.CurrencyResult>(CurrencyFragment::class) {


}