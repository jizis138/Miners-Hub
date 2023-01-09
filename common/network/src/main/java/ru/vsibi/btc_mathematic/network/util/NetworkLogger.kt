package ru.vsibi.btc_mathematic.network.util

import android.util.Log
import io.ktor.client.features.logging.*

class NetworkLogger : Logger {
    override fun log(message: String) {
        Log.d("http", message)
    }
}