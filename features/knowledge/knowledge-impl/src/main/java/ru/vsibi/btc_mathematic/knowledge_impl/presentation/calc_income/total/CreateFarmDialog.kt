/**
 * Created by Dmitry Popov on 22.04.2024.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total

import android.app.Dialog
import androidx.fragment.app.Fragment
import ru.vsibi.btc_mathematic.core.ui.dialog.BottomSheetDialogBuilder
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.DialogCreateFarmBinding
import ru.vsibi.btc_mathematic.util.onClick

object CreateFarmDialog {

    fun create(
        fragment: Fragment,
        onSaveClicked: (String) -> Unit
    ): Dialog {
        val dialogBinding = DialogCreateFarmBinding.inflate(fragment.layoutInflater)

        with(dialogBinding) {
            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            save.onClick {
                onSaveClicked(inputName.text.toString().trim())
                dialog.dismiss()
            }
            return dialog.build()
        }
    }

}