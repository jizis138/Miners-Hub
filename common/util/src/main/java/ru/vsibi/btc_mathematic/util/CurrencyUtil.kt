/**
 * Created by Dmitry Popov on 14.01.2023.
 */
package ru.vsibi.btc_mathematic.util

fun getCurrencyFullNameRes(currency: String) =
    when (currency) {
        "USD" -> {
            R.string.currency_usd
        }
        "EUR" -> {
            R.string.currency_eur
        }
        "RUB" -> {
            R.string.currency_rub
        }
        else -> {
            R.string.currency_usd
        }
    }

fun getCurrencyIcon(currency: String): Int =
    when (currency) {
        "USD" -> {
            R.drawable.ic_currency_dollar
        }
        "EUR" -> {
            R.drawable.ic_currency_euro
        }
        "RUB" -> {
            R.drawable.ic_currency_ruble
        }
        else -> {
            R.drawable.ic_currency_dollar
        }
    }

fun getCurrencySymbol(currency: String?): String =
    when (currency) {
        "USD" -> {
            "$"
        }
        "EUR" -> {
            "€"
        }
        "RUB" -> {
            "₽"
        }
        else -> {
            ""
        }
    }