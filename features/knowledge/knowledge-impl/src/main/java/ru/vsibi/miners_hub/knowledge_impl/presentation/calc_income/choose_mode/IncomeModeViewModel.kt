/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode

import ru.vsibi.miners_hub.knowledge_api.KnowledgeFeature
import ru.vsibi.miners_hub.knowledge_impl.R
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.model.IncomeModeViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.IncomePropertiesEvent
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.IncomePropertiesNavigationContract
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.IncomePropertiesState
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.miners_hub.mvi.BaseViewModel
import ru.vsibi.miners_hub.navigation.RootRouter
import ru.vsibi.miners_hub.navigation.model.RequestParams
import ru.vsibi.miners_hub.util.PrintableText

class IncomeModeViewModel(
    router: RootRouter,
    requestParams: RequestParams
) : BaseViewModel<IncomeModeState, IncomeModeEvent>(
    router, requestParams
) {

    private val incomePropertiesLauncher = launcher(IncomePropertiesNavigationContract){}

    override fun firstState(): IncomeModeState {
        return IncomeModeState(
            items = listOf(
                IncomeModeViewItem(
                    title = PrintableText.Raw("Расчет доходности"),
                    description = PrintableText.Raw("с учетом хэшрейта фермы, стоимости электричества, текущего курса, сложности сети и награды за блок"),
                    onClicked = {
                        incomePropertiesLauncher.launch(KnowledgeFeature.IncomePropertiesParams(
                            mode = KnowledgeFeature.Mode.Normal
                        ))
                    },
                    isLocked = false
                ),
                IncomeModeViewItem(
                    title = PrintableText.Raw("История расчетов"),
                    description = null,
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
