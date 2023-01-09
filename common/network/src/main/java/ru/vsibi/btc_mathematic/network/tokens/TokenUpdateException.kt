package ru.vsibi.btc_mathematic.network.tokens

/**
 * Ошибка обновления токена. В этом случае необходимо разлогинить пользователя
 */
internal class TokenUpdateException(cause: Throwable? = null) : Exception(cause)