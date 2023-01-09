package ru.vsibi.btc_mathematic.presentation.base.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import ru.vsibi.btc_mathematic.mvi.Message
import ru.vsibi.btc_mathematic.mvi.MessageResult
import ru.vsibi.btc_mathematic.mvi.message
import ru.vsibi.btc_mathematic.mvi.messageResult
import ru.vsibi.btc_mathematic.presentation.base.R
import ru.vsibi.btc_mathematic.util.android.bundleOf
import ru.vsibi.btc_mathematic.util.getPrintableText

class MessageDialogFragment : DialogFragment() {

    companion object {

        private const val KEY_RESULT_KEY = "KEY_RESULT_KEY"
        private var Bundle.resultKey: String
            get() = checkNotNull(getString(KEY_RESULT_KEY))
            set(value) = putString(KEY_RESULT_KEY, value)

        fun create(resultKey: String, message: Message.Alert): MessageDialogFragment =
            MessageDialogFragment().apply {
                arguments = bundleOf {
                    this.resultKey = resultKey
                    this.message = message
                }
            }
    }

    private val resultKey by lazy { requireArguments().resultKey }
    private val message by lazy { requireArguments().message as Message.Alert }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(message.titleText?.let { getPrintableText(it) })
            .setMessage(getPrintableText(message.messageText))
            .apply {
                val actionText = message.actionText
                if (actionText != null) {
                    setPositiveButton(getPrintableText(actionText)) { _, _ ->
                        onResult(MessageResult.Action.Positive)
                        dismiss()
                    }
                    setNegativeButton(getString(R.string.common_cancel)) { _, _ ->
                        onResult(MessageResult.Action.Negative)
                        dismiss()
                    }
                } else {
                    setPositiveButton(getString(R.string.common_ok)) { _, _ ->
                        onResult(MessageResult.Action.Positive)
                        dismiss()
                    }
                }
            }
            .create()
            .apply {
                setCanceledOnTouchOutside(message.actionText == null)
            }

    override fun onCancel(dialog: DialogInterface) {
        onResult(MessageResult.Action.Canceled)
        super.onCancel(dialog)
    }

    private fun onResult(action: MessageResult.Action) {
        val result = MessageResult(message.id, action)
        setFragmentResult(resultKey, bundleOf { messageResult = result })
    }
}