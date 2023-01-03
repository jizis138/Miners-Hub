package ru.vsibi.miners_hub.network.util

import android.util.Log
import io.ktor.client.features.logging.*

class NetworkLogger : Logger {
    override fun log(message: String) {
        Log.d("http", message)
    }
}