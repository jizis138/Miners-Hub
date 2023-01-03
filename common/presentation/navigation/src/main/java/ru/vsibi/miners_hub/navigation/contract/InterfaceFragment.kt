package ru.vsibi.miners_hub.navigation.contract

interface InterfaceFragment<out I : Any> {
    fun getInterface(): I
}