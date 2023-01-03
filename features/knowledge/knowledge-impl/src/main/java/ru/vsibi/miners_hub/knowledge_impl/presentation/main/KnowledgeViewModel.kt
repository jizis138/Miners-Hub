/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.main

import ru.vsibi.miners_hub.knowledge_impl.R
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.IncomeModeNavigationContract
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.IncomePropertiesNavigationContract
import ru.vsibi.miners_hub.knowledge_impl.presentation.main.model.KnowledgeViewItem
import ru.vsibi.miners_hub.mvi.BaseViewModel
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.model.RequestParams
import ru.vsibi.miners_hub.util.PrintableText

class KnowledgeViewModel(
    router: RootRouter,
    requestParams: RequestParams
) : BaseViewModel<KnowledgeState, KnowledgeEvent>(
    router, requestParams
) {

    private val incomeCalculationLauncher = launcher(IncomeModeNavigationContract)

    override fun firstState(): KnowledgeState {
        return KnowledgeState(
            items = listOf(
                KnowledgeViewItem(
                    iconRes = R.drawable.ic_money,
                    titleText = PrintableText.Raw(
                        "Расчет доходности фермы"
                    ),
                    onClicked = {
                        incomeCalculationLauncher.launch()
                    }
                )
            )
        )
    }
}