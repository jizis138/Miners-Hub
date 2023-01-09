package ru.vsibi.btc_mathematic.network.client

/**
 * Используется для разлогина пользователя в случае ошибки обновления refresh-токена.
 */
interface ForceLogoutManager {
    suspend fun forceLogout()
}