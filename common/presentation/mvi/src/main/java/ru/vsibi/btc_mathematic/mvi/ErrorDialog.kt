package ru.vsibi.btc_mathematic.mvi
//
//import android.app.Dialog
//import androidx.fragment.app.Fragment
//import ru.vsibi.momento.core.ui.dialog.DialogBuilder
//import ru.vsibi.momento.mvi.databinding.DialogErrorBinding
//import ru.vsibi.momento.util.PrintableText
//import ru.vsibi.momento.util.onClick
//import ru.vsibi.momento.util.setPrintableText
//
//object ErrorDialog {
//
//    fun create(
//        fragment: Fragment,
//        titleText: PrintableText?,
//        messageText: PrintableText
//    ): Dialog {
//        val errorBinding = DialogErrorBinding.inflate(fragment.layoutInflater)
//
//        with(errorBinding) {
//
//            val dialog = DialogBuilder(fragment)
//                .addCustomView(root)
//                .setCancelable(true)
//
//            titleText?.let {
//                title.setPrintableText(it)
//            }
//            description.setPrintableText(messageText)
//
//            close.onClick {
//                dialog.dismiss()
//            }
//            return dialog.build()
//        }
//    }
//
//}
