package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_mode

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.navigation.model.NoResult

object IncomeModeNavigationContract : NavigationContract<IncomeModeNavigationContract.Params, NoResult>(IncomeModeFragment::class) {

    @Parcelize
    data class Params(val usingViaBtc: Boolean = false) : Parcelable
}