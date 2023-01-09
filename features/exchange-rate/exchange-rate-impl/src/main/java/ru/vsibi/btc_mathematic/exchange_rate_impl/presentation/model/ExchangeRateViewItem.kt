package ru.vsibi.btc_mathematic.exchange_rate_impl.presentation.model

import androidx.annotation.DrawableRes
import ru.vsibi.btc_mathematic.util.PrintableText

data class ExchangeRateViewItem(
    val abbreviation: PrintableText,
    val fullCoinName: PrintableText,
    @DrawableRes val iconRes: Int,
    val exchangeRates: ExchangeRate
)

data class ExchangeRate(
    val toRouble: PrintableText
)

