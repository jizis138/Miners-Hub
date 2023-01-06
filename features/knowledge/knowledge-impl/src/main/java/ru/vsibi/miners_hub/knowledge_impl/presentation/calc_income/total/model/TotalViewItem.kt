/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model

import ru.vsibi.miners_hub.knowledge_impl.R
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.TotalViewModel
import ru.vsibi.miners_hub.util.PrintableText

sealed class TotalViewItem {

    data class Results(
        val items: List<ResultViewItem>
    ) : TotalViewItem()

    data class Details(
        val items: List<DetailViewItem>
    ) : TotalViewItem() {
        var expanded = false
        val expandedRes: Int
            get() =
                if (expanded) {
                    R.drawable.ic_baseline_keyboard_arrow_up_24
                } else {
                    R.drawable.ic_baseline_keyboard_arrow_down_24
                }
    }

    object ShareCalculation : TotalViewItem()

}

data class ResultViewItem(
    val title: PrintableText,
    val totalValue: PrintableText
)

data class DetailViewItem(
    val title: PrintableText,
    val description: PrintableText
)