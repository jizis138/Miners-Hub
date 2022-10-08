package ru.vsibi.momento.navigation.contract

interface InterfaceFragment<out I : Any> {
    fun getInterface(): I
}