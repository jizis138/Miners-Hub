/**
 * Created by Dmitry Popov on 01.07.2022.
 */
package ru.vsibi.btc_mathematic.util


data class ErrorInfo(
    val title: PrintableText,
    val description: PrintableText
) {

    companion object {
        fun createEmpty() = ErrorInfo(PrintableText.Raw("Что-то пошло не так"), PrintableText.EMPTY)
    }
}
