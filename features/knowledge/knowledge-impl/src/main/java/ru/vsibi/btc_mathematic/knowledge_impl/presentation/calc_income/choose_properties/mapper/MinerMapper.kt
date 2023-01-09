/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.mapper

import android.content.Context
import ru.vsibi.btc_mathematic.knowledge_api.model.Miner
import ru.vsibi.btc_mathematic.knowledge_impl.domain.logic.CalculationInteractor.Companion.TH
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model.MinerViewItem
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.getPrintableText

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
