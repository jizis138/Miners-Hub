package ru.vsibi.btc_mathematic.navigation.model

import android.os.Parcelable
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContractInterface
import kotlinx.parcelize.Parcelize

@Parcelize
object UnitResult : Parcelable

fun NavigationContractInterface<*, UnitResult>.createResult() = createResult(UnitResult)
