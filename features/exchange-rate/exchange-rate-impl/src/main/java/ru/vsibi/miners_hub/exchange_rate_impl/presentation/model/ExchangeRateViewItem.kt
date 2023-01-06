package ru.vsibi.miners_hub.exchange_rate_impl.presentation.model

import androidx.annotation.DrawableRes
import ru.vsibi.miners_hub.util.PrintableText

data class ExchangeRateViewItem(
    val abbreviation: PrintableText,
    val fullCoinName: PrintableText,
    @DrawableRes val iconRes: Int,
    val exchangeRates: ExchangeRate
)

data class ExchangeRate(
    val toRouble: PrintableText
)

