package ru.vsibi.btc_mathematic.navigation.model

import android.os.Parcelable

abstract class NoResult private constructor() : Parcelable {
    init {
        throw IllegalStateException()
    }
}
