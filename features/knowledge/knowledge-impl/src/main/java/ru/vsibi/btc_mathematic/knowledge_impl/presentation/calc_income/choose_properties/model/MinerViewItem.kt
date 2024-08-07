/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model

import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.util.PrintableText

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
    var name : PrintableText = PrintableText.StringResource(R.string.custom_miner)
}