/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_miner

import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_miner.model.MinerSelectionViewItem

data class MinerSelectionState(
    val miners : List<MinerSelectionViewItem>,
    val addedMiners : List<MinerSelectionViewItem>,
    val isLoad : Boolean
)