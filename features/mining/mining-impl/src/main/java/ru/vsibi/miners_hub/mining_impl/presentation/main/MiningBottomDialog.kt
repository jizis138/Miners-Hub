package ru.vsibi.miners_hub.mining_impl.presentation.main

import android.app.Dialog
import androidx.fragment.app.Fragment
import ru.vsibi.miners_hub.core.ui.dialog.BottomSheetDialogBuilder
import ru.vsibi.miners_hub.mining_impl.databinding.DialogFarmMenuBinding
import ru.vsibi.miners_hub.util.onClick

object MiningBottomDialog {

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