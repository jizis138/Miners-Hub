package ru.vsibi.btc_mathematic.mvi

import androidx.annotation.StringRes
import ru.vsibi.btc_mathematic.util.ErrorInfo
import ru.vsibi.btc_mathematic.util.PrintableText

interface ErrorConverter {
    fun convert(throwable: Throwable): ErrorInfo
}

object DefaultErrorConverter : ErrorConverter {
    override fun convert(throwable: Throwable): ErrorInfo {
        val (@StringRes titleStringRes, @StringRes descriptionStringRes) = when (throwable) {
            else -> R.string.common_error_something_went_wrong_title to R.string.common_error_something_went_wrong_description
        }

        return ErrorInfo(
            title = PrintableText.StringResource(titleStringRes),
            description = PrintableText.StringResource(descriptionStringRes)
        )
    }
}