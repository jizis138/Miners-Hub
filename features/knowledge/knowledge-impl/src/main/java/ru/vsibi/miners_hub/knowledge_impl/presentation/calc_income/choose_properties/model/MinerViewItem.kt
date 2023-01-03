/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model

import ru.vsibi.miners_hub.util.PrintableText

data class MinerViewItem(
    val id: Long,
    val name: PrintableText,
    val hashrate: PrintableText
) {
    var count = 1
}

data class UniversalMinerViewItem(
    val id: Long,
    val name: PrintableText,
) {
    var count = 1
    var power = 100
    var hashrate = 1
}