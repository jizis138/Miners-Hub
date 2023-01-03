/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model

import ru.vsibi.miners_hub.knowledge_impl.domain.entity.Miner
import ru.vsibi.miners_hub.util.PrintableText

sealed class IncomePropertiesViewItem{

    data class MinerSelection(
        val title: PrintableText,
        val items: List<MinerViewItem>,
        val onClicked : () -> Unit
    ) : IncomePropertiesViewItem()

    data class ElectricitySelection(
        val title: PrintableText,
    ) : IncomePropertiesViewItem() {
        var electricityPrice = 0.0
    }
}