package ru.vsibi.momento.navigation.model

import android.os.Parcelable
import ru.vsibi.momento.navigation.contract.NavigationContractInterface
import kotlinx.parcelize.Parcelize

@Parcelize
object UnitResult : Parcelable

fun NavigationContractInterface<*, UnitResult>.createResult() = createResult(UnitResult)
