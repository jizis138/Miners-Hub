/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model

import ru.vsibi.miners_hub.util.PrintableText

data class MinerViewItem(
    val id: Long,
    val name: PrintableText,
    val description : PrintableText
) {
    var count = 1
}

data class UniversalMinerViewItem(
    val id: Long,
) {
    var count = 1
    var power = 1000
    var hashrate = 100
    var name = "Универсальный майнер"
}