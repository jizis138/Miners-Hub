package ru.vsibi.momento.mvi

import androidx.annotation.StringRes
import ru.vsibi.momento.core.exceptions.InternetConnectionException
import ru.vsibi.momento.core.exceptions.WrongServerResponseException
import ru.vsibi.momento.util.ErrorInfo
import ru.vsibi.momento.util.PrintableText

interface ErrorConverter {
    fun convert(throwable: Throwable): ErrorInfo
}

object DefaultErrorConverter : ErrorConverter {
    override fun convert(throwable: Throwable): ErrorInfo {
//        val (@StringRes titleStringRes, @StringRes descriptionStringRes) = when (throwable) {
//            is InternetConnectionException -> R.string.common_error_connection_error_title to R.string.common_error_connection_error_description
//            is WrongServerResponseException -> R.string.common_error_server_title to R.string.common_error_server_description
//            else -> R.string.common_error_something_went_wrong_title to R.string.common_error_something_went_wrong_description
//        }

        return ErrorInfo(
            title = PrintableText.StringResource(0),
            description = PrintableText.StringResource(0)
        )
    }
}