package ru.vsibi.btc_mathematic.mvi


sealed interface ShowErrorType {
    object Popup : ShowErrorType
    object Alert : ShowErrorType
    object Dialog : ShowErrorType
}