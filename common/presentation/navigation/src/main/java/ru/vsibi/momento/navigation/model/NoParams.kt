package ru.vsibi.momento.navigation.model

import android.os.Parcelable
import ru.vsibi.momento.navigation.contract.ComponentFragmentContractApi
import ru.vsibi.momento.navigation.contract.NavigationContractApi
import kotlinx.parcelize.Parcelize

@Parcelize
object NoParams : Parcelable

fun NavigationContractApi<NoParams, *>.createParams() = createParams(NoParams)

fun ComponentFragmentContractApi<*, NoParams>.createParams() = createParams(NoParams)