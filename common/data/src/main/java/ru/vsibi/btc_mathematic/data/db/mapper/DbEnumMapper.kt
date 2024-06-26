package ru.vsibi.btc_mathematic.data.db.mapper

object DbEnumMapper {

    fun <E : Enum<E>> serialize(e: E): String = e.name

    inline fun <reified E : Enum<E>> deserializeOrNull(string: String): E? =
        runCatching { enumValueOf<E>(string) }.getOrNull()

    inline fun <reified E : Enum<E>> deserialize(string: String, fallback: E): E =
        runCatching { enumValueOf<E>(string) }.getOrDefault(fallback)
}