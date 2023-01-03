/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.model

import ru.vsibi.miners_hub.util.PrintableText

data class IncomeModeViewItem(
    val title: PrintableText,
    val description: PrintableText?,
    val onClicked : () -> Unit
)