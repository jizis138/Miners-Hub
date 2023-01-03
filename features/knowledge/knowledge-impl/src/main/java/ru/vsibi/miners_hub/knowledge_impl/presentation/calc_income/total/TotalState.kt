/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total

import ru.vsibi.miners_hub.knowledge_impl.domain.logic.CalculationInteractor
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.TotalViewItem
import ru.vsibi.miners_hub.util.PrintableText

data class TotalState(
    val calculationText : PrintableText?,
    val items : List<TotalViewItem>
)