/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_mode

import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.knowledge_impl.R
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_mode.model.IncomeModeViewItem
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.IncomePropertiesNavigationContract
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.HistoryNavigationContract
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.util.PrintableText

class IncomeModeViewModel(
    router: RootRouter,
    requestParams: RequestParams
) : BaseViewModel<IncomeModeState, IncomeModeEvent>(
    router, requestParams
) {

    private val incomePropertiesLauncher = launcher(IncomePropertiesNavigationContract){}

    private val historyLauncher = launcher(HistoryNavigationContract)

    override fun firstState(): IncomeModeState {
        return IncomeModeState(
            items = listOf(
                IncomeModeViewItem(
                    title = PrintableText.StringResource(R.string.calc_income),
                    description = PrintableText.StringResource(R.string.calc_income_desc),
                    onClicked = {
                        incomePropertiesLauncher.launch(KnowledgeFeature.IncomePropertiesParams(
                            mode = KnowledgeFeature.Mode.Normal
                        ))
                    },
                    isLocked = false
                ),
                IncomeModeViewItem(
                    title = PrintableText.StringResource(R.string.calc_history),
                    description = null,
                    onClicked = {
                        historyLauncher.launch()
                    },
                    isLocked = false
                ),
            )
        )
    }

}
