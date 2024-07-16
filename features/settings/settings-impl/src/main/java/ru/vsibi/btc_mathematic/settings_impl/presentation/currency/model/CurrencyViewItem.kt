/**
 * Created by Dmitry Popov on 14.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.currency.model

import ru.vsibi.btc_mathematic.util.PrintableText

data class CurrencyViewItem(
    val currencyName: String,
    val title: PrintableText,
    var isSelected: Boolean
)