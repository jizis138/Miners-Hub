/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model

import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.TotalViewModel
import ru.vsibi.miners_hub.util.PrintableText

sealed class TotalViewItem {

    data class Results(
        val items: List<ResultViewItem>
    ) : TotalViewItem()

}

data class ResultViewItem(
    val title: PrintableText,
    val totalValue: PrintableText
)