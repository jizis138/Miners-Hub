/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties

import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.MinerViewItem
import ru.vsibi.miners_hub.util.PrintableText

data class IncomePropertiesState(
    val needUpdateList : Boolean,
    val items : List<IncomePropertiesViewItem>,
    val toolbarTitle : PrintableText
)