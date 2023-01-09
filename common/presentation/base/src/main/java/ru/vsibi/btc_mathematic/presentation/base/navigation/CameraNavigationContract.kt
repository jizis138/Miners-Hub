package ru.vsibi.btc_mathematic.presentation.base.navigation

import android.content.Intent
import android.graphics.Bitmap
import android.os.Parcelable
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import ru.vsibi.btc_mathematic.navigation.contract.NavigationContract
import ru.vsibi.btc_mathematic.navigation.model.NoParams
import ru.vsibi.btc_mathematic.presentation.base.ui.ActivityResultFragment
import kotlinx.parcelize.Parcelize

object CameraNavigationContract :
    NavigationContract<NoParams, CameraNavigationContract.Result>(
        CameraResultFragment::class
    ) {

    @Parcelize
    data class Result(val bitmap: Bitmap?) : Parcelable
}

class CameraResultFragment :
    ActivityResultFragment<NoParams, CameraNavigationContract.Result, Intent, ActivityResult?>() {

    override val navigationContract = CameraNavigationContract
    override val activityContract = ActivityResultContracts.StartActivityForResult()

    override val mapParams: (NoParams) -> Intent = { Intent(MediaStore.ACTION_IMAGE_CAPTURE) }
    override val mapResult: (ActivityResult?) -> CameraNavigationContract.Result =
        { CameraNavigationContract.Result(bitmap = it?.data?.extras?.get("data") as? Bitmap) }
}