/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.main

import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_mode.IncomeModeNavigationContract
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.main.model.KnowledgeViewItem
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.util.PrintableText

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
                    },
                    isLocked = false
                ),
                KnowledgeViewItem(
                    iconRes = R.drawable.ic_baseline_help_24,
                    titleText = PrintableText.Raw(
                        "Обучение"
                    ),
                    onClicked = {
                        showPopup(PrintableText.StringResource(
                            R.string.locked
                        ))
                    },
                    isLocked = true
                ),
            )
        )
    }
}