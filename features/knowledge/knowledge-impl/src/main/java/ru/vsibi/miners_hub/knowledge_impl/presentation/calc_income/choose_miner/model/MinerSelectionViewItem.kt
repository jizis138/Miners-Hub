package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.model

import ru.vsibi.miners_hub.util.PrintableText

data class MinerSelectionViewItem(
    val id: Long,
    val name: PrintableText,
    val hashrate: PrintableText
) {

    var count = 0

    val isSelected: Boolean
        get() = count > 0

    val backgroundRes: Int
        get() = if (isSelected) {
            ru.vsibi.miners_hub.uikit.R.color.dark_light
        } else {
            android.R.color.transparent
        }
}
