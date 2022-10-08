package ru.vsibi.momento.navigation.model

import android.os.Parcelable

abstract class NoResult private constructor() : Parcelable {
    init {
        throw IllegalStateException()
    }
}
