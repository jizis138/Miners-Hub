/**
 * Created by Dmitry Popov on 14.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.language.model

import ru.vsibi.btc_mathematic.util.PrintableText

data class LanguageViewItem(
    val localeName: String,
    val title: PrintableText,
    var isSelected: Boolean
)