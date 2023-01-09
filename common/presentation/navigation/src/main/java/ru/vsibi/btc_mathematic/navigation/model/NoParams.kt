package ru.vsibi.btc_mathematic.navigation.model

import android.os.Parcelable
import ru.vsibi.btc_mathematic.navigation.contract.ComponentFragmentContractApi
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContractApi
import kotlinx.parcelize.Parcelize

@Parcelize
object NoParams : Parcelable

fun NavigationContractApi<NoParams, *>.createParams() = createParams(NoParams)

fun ComponentFragmentContractApi<*, NoParams>.createParams() = createParams(NoParams)