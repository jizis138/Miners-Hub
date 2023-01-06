/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.mapper

import android.content.Context
import ru.vsibi.miners_hub.knowledge_api.model.Miner
import ru.vsibi.miners_hub.knowledge_api.model.Schema
import ru.vsibi.miners_hub.knowledge_impl.domain.logic.CalculationInteractor.Companion.TH
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.MinerViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.UniversalMinerViewItem
import ru.vsibi.miners_hub.util.PrintableText
import ru.vsibi.miners_hub.util.getPrintableRawText
import ru.vsibi.miners_hub.util.getPrintableText

class MinerMapper(private val appContext: Context) {
    fun mapMinersToViewItem(miners: List<Miner>): List<MinerViewItem> = miners.map { miner->
        val schema = miner.schemas.firstOrNull()
        val hashrate = schema?.hashrate?.div(TH) ?: ""
        val power = schema?.power ?: ""
        MinerViewItem(
            id = miner.id,
            name = PrintableText.Raw(miner.name),
            description = PrintableText.Raw("$hashrate TH, $power Вт")
        ).also {
            it.count = miner.count
        }
    }

    fun mapViewItemsToMiners(viewItems: List<MinerViewItem>, allMiners: List<Miner>): List<Miner> {
        val selectedMiners = mutableListOf<Miner>()
        viewItems.forEach { viewItem ->
            allMiners.find { miner ->
                miner.name == appContext.getPrintableText(viewItem.name)
            }?.let { miner ->
                selectedMiners.add(miner)
            }
        }
        return selectedMiners
    }

//    fun mapViewItemsToMiners(viewItems: List<UniversalMinerViewItem>): List<Miner> {
//        val selectedMiners = mutableListOf<Miner>()
//        viewItems.forEach { viewItem ->
//            repeat(viewItem.count) {
//                selectedMiners.add(
//                    Miner(
//                        id = viewItem.id,
//                        name = viewItem.name,
//                        schemas = listOf(
//                            Schema(
//                                algorithmName = "SHA-256",
//                                hashrate = viewItem.hashrate * TH,
//                                power = viewItem.power.toLong(),
//
//                            )
//                        ),
//                        count = viewItem.count
//                    )
//                )
//            }
//        }
//        return selectedMiners
//    }
}
