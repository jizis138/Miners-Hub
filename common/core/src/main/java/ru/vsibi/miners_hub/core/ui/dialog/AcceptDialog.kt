package ru.vsibi.miners_hub.core.ui.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import ru.vsibi.miners_hub.core.databinding.DialogAcceptBinding
import ru.vsibi.miners_hub.util.PrintableText
import ru.vsibi.miners_hub.util.onClick
import ru.vsibi.miners_hub.util.setPrintableText

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