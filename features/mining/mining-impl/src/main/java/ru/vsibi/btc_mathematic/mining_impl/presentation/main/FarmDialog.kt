package ru.vsibi.btc_mathematic.mining_impl.presentation.main

import android.app.Dialog
import androidx.fragment.app.Fragment
import ru.vsibi.btc_mathematic.core.ui.dialog.BottomSheetDialogBuilder
import ru.vsibi.btc_mathematic.mining_impl.databinding.DialogFarmMenuBinding
import ru.vsibi.btc_mathematic.util.onClick

object FarmDialog {

    fun create(
        fragment: Fragment,
        onEditClicked: () -> Unit,
        onRemoveClicked: () -> Unit
    ): Dialog {
        val dialogBinding = DialogFarmMenuBinding.inflate(fragment.layoutInflater)

        with(dialogBinding) {
            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            dialogBinding.edit.onClick {
                onEditClicked()
                dialog.dismiss()
            }
            dialogBinding.remove.onClick {
                onRemoveClicked()
                dialog.dismiss()
            }
            return dialog.build()
        }
    }

}