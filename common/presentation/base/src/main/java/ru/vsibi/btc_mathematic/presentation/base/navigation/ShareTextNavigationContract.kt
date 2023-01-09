package ru.vsibi.btc_mathematic.presentation.base.navigation

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract
import ru.vsibi.btc_mathematic.presentation.base.ui.ActivityResultFragment
import kotlinx.parcelize.Parcelize
import ru.vsibi.btc_mathematic.navigation.model.UnitResult

object ShareTextNavigationContract :
    NavigationContract<ShareTextNavigationContract.Params, UnitResult>(
        ShareTextResultFragment::class
    ) {

    @Parcelize
    data class Params(val text: String) : Parcelable

}

object ShareTextActivityContract :
    ActivityResultContract<String, Unit>() {
    override fun createIntent(
        context: Context,
        input: String?
    ): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, input ?: "")
            type = "text/plain"
        }

        return Intent.createChooser(sendIntent, null)
    }

    override fun parseResult(resultCode: Int, intent: Intent?) = Unit
}

class ShareTextResultFragment :
    ActivityResultFragment<ShareTextNavigationContract.Params, UnitResult, String, Unit>() {

    override val navigationContract = ShareTextNavigationContract
    override val activityContract = ShareTextActivityContract

    override val mapParams: (ShareTextNavigationContract.Params) -> String = { it.text }
    override val mapResult: (Unit) -> UnitResult = { UnitResult }
}