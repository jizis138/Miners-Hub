/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.mapper

import android.content.Context
import android.content.res.Resources
import ru.vsibi.miners_hub.knowledge_api.model.Miner
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.model.MinerSelectionViewItem
import ru.vsibi.miners_hub.util.PrintableText
import ru.vsibi.miners_hub.util.getPrintableText

class MinerSelectionMapper(
    private val context : Context
) {
    fun mapMinersToViewItem(
        miners: List<Miner>,
        addedMiners: List<MinerSelectionViewItem>
    ) = miners.map { miner ->
        val schema = miner.schemas.firstOrNull()
        val hashrate = schema?.hashrate?.div(1000000000000f) ?: ""

        MinerSelectionViewItem(
            id = miner.id,
            name = PrintableText.Raw(miner.name),
            hashrate = PrintableText.Raw("$hashrate TH")
        ).also {
            it.count = addedMiners.find { minerSelectionViewItem ->
                minerSelectionViewItem.id == miner.id || miner.name == context.getPrintableText(minerSelectionViewItem.name)
            }?.count ?: miner.count
        }
    }
}
