/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model

import ru.vsibi.btc_mathematic.util.PrintableText

sealed class IncomePropertiesViewItem {

    data class MinerSelection(
        val title: PrintableText,
        val items: List<MinerViewItem>,
        val onClicked: () -> Unit
    ) : IncomePropertiesViewItem()

    data class UniversalSelection(
        val title: PrintableText,
        val items: List<UniversalMinerViewItem>,
        val onClicked: () -> Unit
    ) : IncomePropertiesViewItem()

    data class ElectricitySelection(
        val title: PrintableText,
    ) : IncomePropertiesViewItem() {
        var electricityPrice = 0.0
    }
}