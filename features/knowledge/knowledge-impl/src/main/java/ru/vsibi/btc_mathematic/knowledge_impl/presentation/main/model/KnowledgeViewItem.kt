/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.main.model

import androidx.annotation.DrawableRes
import ru.vsibi.btc_mathematic.util.PrintableText

data class KnowledgeViewItem(
    @DrawableRes val iconRes: Int,
    val titleText: PrintableText,
    val onClicked: () -> Unit,
    val isLocked: Boolean
)