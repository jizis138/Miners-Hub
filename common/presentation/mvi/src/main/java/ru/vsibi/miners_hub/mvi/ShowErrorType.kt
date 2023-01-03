package ru.vsibi.miners_hub.mvi


sealed interface ShowErrorType {
    object Popup : ShowErrorType
    object Alert : ShowErrorType
    object Dialog : ShowErrorType
}