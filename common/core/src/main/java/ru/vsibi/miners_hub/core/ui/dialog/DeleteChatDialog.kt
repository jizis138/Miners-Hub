package ru.vsibi.miners_hub.core.ui.dialog

//import android.app.Dialog
//import androidx.fragment.app.Fragment
//import ru.vsibi.momento.core.databinding.DialogDeleteChatBinding
//import ru.vsibi.momento.util.PrintableText
//import ru.vsibi.momento.util.onClick
//import ru.vsibi.momento.util.setPrintableText
//
//object DeleteChatDialog {
//
//    fun create(
//        fragment: Fragment,
//        titleText: PrintableText?,
//        accept: (id: Long) -> Unit,
//        id : Long
//    ): Dialog {
//        val binding = DialogDeleteChatBinding.inflate(fragment.layoutInflater)
//
//        with(binding) {
//
//            val dialog = DialogBuilder(fragment)
//                .addCustomView(root)
//                .setCancelable(true)
//
//            titleText?.let {
//                title.setPrintableText(it)
//            }
//
//            delete.onClick {
//                accept(id)
//                dialog.dismiss()
//            }
//
//            cancel.onClick {
//                dialog.dismiss()
//            }
//            return dialog.build()
//        }
//    }
//}