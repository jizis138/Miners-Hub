package ru.vsibi.miners_hub.navigation.model

import android.os.Parcelable

abstract class NoResult private constructor() : Parcelable {
    init {
        throw IllegalStateException()
    }
}
