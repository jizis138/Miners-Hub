/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total

import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total.model.TotalViewItem
import ru.vsibi.btc_mathematic.util.PrintableText

data class TotalState(
    val calculationText : PrintableText?,
    val items : List<TotalViewItem>
)