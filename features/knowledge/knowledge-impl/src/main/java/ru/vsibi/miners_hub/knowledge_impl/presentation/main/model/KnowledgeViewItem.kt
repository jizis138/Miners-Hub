/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.main.model

import androidx.annotation.DrawableRes
import ru.vsibi.miners_hub.util.PrintableText

data class KnowledgeViewItem(
    @DrawableRes val iconRes: Int,
    val titleText: PrintableText,
    val onClicked: () -> Unit,
    val isLocked: Boolean
)