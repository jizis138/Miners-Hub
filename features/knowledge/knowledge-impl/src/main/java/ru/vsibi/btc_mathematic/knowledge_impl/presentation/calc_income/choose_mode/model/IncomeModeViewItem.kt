/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_mode.model

import ru.vsibi.btc_mathematic.util.PrintableText

data class IncomeModeViewItem(
    val title: PrintableText,
    val description: PrintableText?,
    val onClicked : () -> Unit,
    val isLocked : Boolean
)