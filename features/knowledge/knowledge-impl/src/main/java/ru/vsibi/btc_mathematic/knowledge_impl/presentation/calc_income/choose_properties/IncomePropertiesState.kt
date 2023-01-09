/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties

import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.btc_mathematic.util.PrintableText

data class IncomePropertiesState(
    val needUpdateList : Boolean,
    val items : List<IncomePropertiesViewItem>,
    val toolbarTitle : PrintableText
)