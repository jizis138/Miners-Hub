package ru.vsibi.momento.mvi


sealed interface ShowErrorType {
    object Popup : ShowErrorType
    object Alert : ShowErrorType
    object Dialog : ShowErrorType
}