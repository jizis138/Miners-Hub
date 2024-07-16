/**
 * Created by Dmitry Popov on 14.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.currency

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.settings_api.SettingsFeature
import ru.vsibi.btc_mathematic.settings_impl.R
import ru.vsibi.btc_mathematic.settings_impl.domain.logic.LocaleManager
import ru.vsibi.btc_mathematic.settings_impl.presentation.currency.model.CurrencyViewItem
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.getCurrencyFullNameRes

class CurrencyViewModel(
    rootRouter: RootRouter,
    requestParams: RequestParams,
    private val localeManager: LocaleManager
) : BaseViewModel<CurrencyState, Nothing>(rootRouter, requestParams) {

    override fun firstState(): CurrencyState {
        val selectedCurrency = runBlocking { localeManager.getSavedCurrency() }
        return CurrencyState(
            listOf(
                CurrencyViewItem(
                    currencyName = "USD",
                    title = PrintableText.StringResource(getCurrencyFullNameRes("USD")),
                    isSelected = selectedCurrency == "USD"
                ),
                CurrencyViewItem(
                    currencyName = "EUR",
                    title = PrintableText.StringResource(getCurrencyFullNameRes("EUR")),
                    isSelected = selectedCurrency == "EUR"
                ),
                CurrencyViewItem(
                    currencyName = "RUB",
                    title = PrintableText.StringResource(getCurrencyFullNameRes("RUB")),
                    isSelected = selectedCurrency == "RUB"
                ),
            )
        )
    }

    fun onCurrencyClicked(currencyViewItem: CurrencyViewItem) {
        viewModelScope.launch {
            localeManager.changeCurrency(currencyViewItem.currencyName)
            exitWithResult(
                CurrencyNavigationContract.createResult(
                    SettingsFeature.CurrencyResult.CurrencyChanged(
                        currencyViewItem.currencyName
                    )
                )
            )
        }
    }

}