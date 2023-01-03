package ru.vsibi.miners_hub.network.tokens

/**
 * Ошибка обновления токена. В этом случае необходимо разлогинить пользователя
 */
internal class TokenUpdateException(cause: Throwable? = null) : Exception(cause)