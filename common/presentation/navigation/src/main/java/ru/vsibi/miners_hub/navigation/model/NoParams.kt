package ru.vsibi.miners_hub.navigation.model

import android.os.Parcelable
import ru.vsibi.miners_hub.navigation.contract.ComponentFragmentContractApi
import ru.vsibi.miners_hub.navigation.contract.NavigationContractApi
import kotlinx.parcelize.Parcelize

@Parcelize
object NoParams : Parcelable

fun NavigationContractApi<NoParams, *>.createParams() = createParams(NoParams)

fun ComponentFragmentContractApi<*, NoParams>.createParams() = createParams(NoParams)