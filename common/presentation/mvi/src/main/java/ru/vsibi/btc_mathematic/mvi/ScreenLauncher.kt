package ru.vsibi.btc_mathematic.mvi

import android.os.Parcelable
import androidx.activity.result.ActivityResultLauncher
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContractApi

/**
 * Inspired by [ActivityResultLauncher]. Intended to use with fragments.
 */
interface ScreenLauncher<in P : Parcelable> {
    fun launch(p: P)
}

class ScreenLauncherImpl<in P : Parcelable, R : Parcelable>(
    private val router: RootRouter,
    private val targetRequestKey: String,
    val navigationContractApi: NavigationContractApi<P, R>,
    val consumeResult: (R) -> Unit,
) : ScreenLauncher<P> {
    override fun launch(p: P) {
        router.navigateTo(
            createParamsInterface = navigationContractApi.createParams(p),
            targetRequestKey = targetRequestKey,
        )
    }
}