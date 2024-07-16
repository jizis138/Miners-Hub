/**
 * Created by Dmitry Popov on 10.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.settings.model

import androidx.annotation.DrawableRes
import ru.vsibi.btc_mathematic.util.PrintableText

class SettingsViewItem(
    @DrawableRes val icon : Int,
    val title : PrintableText,
    inline val onItemClicked : () -> Unit,
    val isLocked : Boolean
)