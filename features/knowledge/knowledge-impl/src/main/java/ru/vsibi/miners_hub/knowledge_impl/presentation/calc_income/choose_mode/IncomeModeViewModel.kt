/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode

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

    private val incomePropertiesLauncher = launcher(IncomePropertiesNavigationContract)

    override fun firstState(): IncomeModeState {
        return IncomeModeState(
            items = listOf(
                IncomeModeViewItem(
                    title = PrintableText.Raw("Расчет доходности"),
                    description = PrintableText.Raw("на примере существующих майнеров, с учетом стоимости электричества, текущего курса и сложности"),
                    onClicked = {
                        incomePropertiesLauncher.launch(
                            IncomePropertiesNavigationContract.Params(
                                calculationMode = IncomePropertiesNavigationContract.CalculationMode.Normal
                            )
                        )
                    }
                ),
                IncomeModeViewItem(
                    title = PrintableText.Raw("Универсальные расчеты"),
                    description = PrintableText.Raw("с любой мощностью и потреблением майнеров"),
                    onClicked = {
                        incomePropertiesLauncher.launch(
                            IncomePropertiesNavigationContract.Params(
                                calculationMode = IncomePropertiesNavigationContract.CalculationMode.Universal
                            )
                        )
                    }
                ),
                IncomeModeViewItem(
                    title = PrintableText.Raw("История расчетов"),
                    description = null,
                    onClicked = {}
                ),
            )
        )
    }

}
