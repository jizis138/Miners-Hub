package ru.vsibi.miners_hub.util

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

fun JsonObject.toMap(): Map<String, *> = keys.asSequence().associateWith {
    when (val value = this[it]) {
        is JsonArray -> {
            val map = (0 until value.size).associate { Pair(it.toString(), value[it]) }
            JsonObject(map).toMap().values.toList()
        }
        is JsonObject -> value.toMap()
        else -> value
    }
}
