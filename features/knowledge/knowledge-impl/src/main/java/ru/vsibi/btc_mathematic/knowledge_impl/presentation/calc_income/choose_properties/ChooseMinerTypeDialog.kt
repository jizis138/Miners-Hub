package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties

import android.app.Dialog
import androidx.fragment.app.Fragment
import ru.vsibi.btc_mathematic.core.ui.dialog.BottomSheetDialogBuilder
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.DialogChooseMinerTypeBinding
import ru.vsibi.btc_mathematic.util.onClick

object ChooseMinerTypeDialog {

    fun create(
        fragment: Fragment,
        onMinerClicked: () -> Unit,
        onUniversalClicked: () -> Unit
    ): Dialog {
        val dialogBinding = DialogChooseMinerTypeBinding.inflate(fragment.layoutInflater)

        with(dialogBinding) {
            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            dialogBinding.miner.onClick {
                onMinerClicked()
            }
            dialogBinding.universal.onClick {
                onUniversalClicked()
            }
            return dialog.build()
        }
    }
}