/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract
import ru.vsibi.btc_mathematic.navigation.model.NoResult

object HistoryNavigationContract :
    NavigationContract<HistoryNavigationContract.Params, NoResult>(HistoryFragment::class) {

    @Parcelize
    data class Params(
        val usingViaBTC: Boolean
    ) : Parcelable

}