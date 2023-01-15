/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model

import ru.vsibi.btc_mathematic.knowledge_api.model.Price
import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.getCurrencySymbol

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
        val currencySymbol: String,
    ) : IncomePropertiesViewItem() {
        var electricityPrice = 0.0

        val currencyText: PrintableText
            get() = PrintableText.StringResource(R.string.currency_per_kilowat, currencySymbol)
    }

    data class ExchangeRateSelection(
        val title: PrintableText,
        val currency : String
    ) : IncomePropertiesViewItem() {
        var exchangeRate = 0L
        val currencySymbol : String
            get() = getCurrencySymbol(currency)
    }

    class FarmNameSelection : IncomePropertiesViewItem() {
        var farmName = ""
    }

    data class CurrencySelection(
        val title: PrintableText,
        val iconRes: Int,
        val currencyName: String,
        val onClicked: () -> Unit
    ) : IncomePropertiesViewItem()
}