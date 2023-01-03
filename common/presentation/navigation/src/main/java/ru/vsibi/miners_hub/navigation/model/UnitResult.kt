package ru.vsibi.miners_hub.navigation.model

import android.os.Parcelable
import ru.vsibi.miners_hub.navigation.contract.NavigationContractInterface
import kotlinx.parcelize.Parcelize

@Parcelize
object UnitResult : Parcelable

fun NavigationContractInterface<*, UnitResult>.createResult() = createResult(UnitResult)
