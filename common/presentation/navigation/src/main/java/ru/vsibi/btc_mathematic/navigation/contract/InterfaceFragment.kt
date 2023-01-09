package ru.vsibi.btc_mathematic.navigation.contract

interface InterfaceFragment<out I : Any> {
    fun getInterface(): I
}