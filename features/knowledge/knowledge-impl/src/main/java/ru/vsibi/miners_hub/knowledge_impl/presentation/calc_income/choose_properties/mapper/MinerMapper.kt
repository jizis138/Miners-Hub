/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.mapper

import android.content.Context
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Miner
import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Schema
import ru.vsibi.miners_hub.knowledge_impl.domain.logic.CalculationInteractor.Companion.TH
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.MinerViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.UniversalMinerViewItem
import ru.vsibi.miners_hub.util.PrintableText
import ru.vsibi.miners_hub.util.getPrintableRawText
import ru.vsibi.miners_hub.util.getPrintableText

class MinerMapper(private val appContext: Context) {
    fun mapMinersToViewItem(miners: List<Miner>): List<MinerViewItem> = miners.map {
        val schema = it.schemas.firstOrNull()
        val hashrate = schema?.hashrate?.div(1000000000000f) ?: ""
        MinerViewItem(
            id = it.id,
            name = PrintableText.Raw(it.name),
            hashrate = PrintableText.Raw("$hashrate TH")
        )
    }

    fun mapViewItemsToMiners(viewItems: List<MinerViewItem>, allMiners: List<Miner>): List<Miner> {
        val selectedMiners = mutableListOf<Miner>()
        viewItems.forEach { viewItem ->
            allMiners.find { miner ->
                miner.name == appContext.getPrintableText(viewItem.name)
            }?.let { miner ->
                repeat(viewItem.count) {
                    selectedMiners.add(miner)
                }
            }
        }
        return selectedMiners
    }

    fun mapViewItemsToMiners(viewItems: List<UniversalMinerViewItem>): List<Miner> {
        val selectedMiners = mutableListOf<Miner>()
        viewItems.forEach { viewItem ->
            repeat(viewItem.count) {
                selectedMiners.add(
                    Miner(
                        id = viewItem.id,
                        name = getPrintableRawText(viewItem.name),
                        schemas = listOf(Schema(
                            algorithmName = "SHA-256",
                            hashrate = viewItem.hashrate * TH,
                            power = viewItem.power.toLong()
                        ))
                    )
                )
            }
        }
        return selectedMiners
    }
}
