package ru.vsibi.miners_hub.presentation.base.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.ModalWindow
import ru.vsibi.miners_hub.navigation.contract.NavigationContractInterface
import ru.vsibi.miners_hub.navigation.model.requestParams
import org.koin.android.ext.android.inject

abstract class ActivityResultFragment<P : Parcelable, R : Parcelable, I, O> :
    Fragment(),
    ModalWindow {

    abstract val navigationContract: NavigationContractInterface<P, R>
    abstract val activityContract: ActivityResultContract<I, O>

    abstract val mapParams: (P) -> I
    abstract val mapResult: (O) -> R

    private val router: RootRouter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityContractWrapper = object : ActivityResultContract<I, O>() {
            override fun createIntent(context: Context, input: I): Intent = Intent.createChooser(
                activityContract.createIntent(context, input),
                "Выберите приложение"
            )

            override fun parseResult(resultCode: Int, intent: Intent?): O =
                activityContract.parseResult(resultCode, intent)

            override fun getSynchronousResult(context: Context, input: I): SynchronousResult<O>? =
                activityContract.getSynchronousResult(context, input)
        }

        registerForActivityResult(activityContractWrapper) { result ->
            router.exitWithResult(
                checkNotNull(requestParams.resultKey),
                navigationContract.createResult(mapResult(result))
            )
        }.launch(
            mapParams(navigationContract.getParams.getParams(this))
        )
    }
}