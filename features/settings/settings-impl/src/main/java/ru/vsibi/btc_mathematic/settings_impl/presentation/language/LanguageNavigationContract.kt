package ru.vsibi.btc_mathematic.settings_impl.presentation.language

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract
import ru.vsibi.btc_mathematic.navigation.model.NoParams

object LanguageNavigationContract :
    NavigationContract<NoParams, LanguageNavigationContract.Result>(LanguageFragment::class) {

    sealed class Result : Parcelable {

        @Parcelize
        data class LanguageChanged(val locale: String) : Result(), Parcelable

        @Parcelize
        object Cancel : Result(), Parcelable
    }

}