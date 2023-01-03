/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.mapper

import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Miner
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.model.MinerSelectionViewItem
import ru.vsibi.miners_hub.util.PrintableText

object MinerSelectionMapper {
    fun mapMinersToViewItem(miners: List<Miner>) = miners.map {
        val schema = it.schemas.firstOrNull()
        val hashrate = schema?.hashrate?.div(1000000000000f) ?: ""
        MinerSelectionViewItem(
            id = it.id,
            name = PrintableText.Raw(it.name),
            hashrate = PrintableText.Raw("$hashrate TH")
        )
    }
}
