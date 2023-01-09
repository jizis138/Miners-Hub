package ru.vsibi.btc_mathematic.core.ui.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import ru.vsibi.btc_mathematic.core.databinding.DialogAcceptBinding
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.onClick
import ru.vsibi.btc_mathematic.util.setPrintableText

object AcceptDialog {

    fun create(
        fragment: Fragment,
        title : PrintableText,
        onAcceptClicked: () -> Unit
    ): Dialog {
        val dialogBinding = DialogAcceptBinding.inflate(fragment.layoutInflater)

        with(dialogBinding) {
            val dialog = DialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)


            dialogBinding.title.setPrintableText(title)
            dialogBinding.accept.onClick {
                onAcceptClicked()
                dialog.dismiss()
            }
            dialogBinding.cancel.onClick {
                dialog.dismiss()
            }
            return dialog.build()
        }
    }
    
}