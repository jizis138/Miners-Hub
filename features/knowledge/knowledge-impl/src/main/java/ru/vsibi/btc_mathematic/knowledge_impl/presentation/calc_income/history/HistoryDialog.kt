package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history

import android.app.Dialog
import androidx.fragment.app.Fragment
import ru.vsibi.btc_mathematic.core.ui.dialog.BottomSheetDialogBuilder
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.DialogHistoryMenuBinding
import ru.vsibi.btc_mathematic.util.onClick

object HistoryDialog {

    fun create(
        fragment: Fragment,
        onRemoveClicked: () -> Unit
    ): Dialog {
        val dialogBinding = DialogHistoryMenuBinding.inflate(fragment.layoutInflater)

        with(dialogBinding) {
            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            dialogBinding.remove.onClick {
                onRemoveClicked()
                dialog.dismiss()
            }
            return dialog.build()
        }
    }

}