package ru.vsibi.momento.core

import ru.vsibi.momento.util.PrintableText


data class ErrorInfo(
    val title: PrintableText,
    val description: PrintableText,
)
