package ru.vsibi.btc_mathematic.exchange_rate_impl.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vsibi.btc_mathematic.exchange_rate_impl.R
import ru.vsibi.btc_mathematic.exchange_rate_impl.presentation.model.ExchangeRate
import ru.vsibi.btc_mathematic.exchange_rate_impl.presentation.model.ExchangeRateViewItem
import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.uikit.LoadingState
import ru.vsibi.btc_mathematic.util.ErrorInfo
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.getCurrencySymbol
import kotlin.math.roundToInt

class ExchangeRateViewModel(
    rootRouter: RootRouter,
    requestParams: RequestParams,
    private val knowledgeFeature: KnowledgeFeature
) : BaseViewModel<ExchangeRateState, ExchangeRateEvent>(
    rootRouter, requestParams
) {

    init {
        loadExchangeRates()
    }

    override fun firstState(): ExchangeRateState {
        return ExchangeRateState(
            loadingState = LoadingState.None
        )
    }

    fun loadExchangeRates() {
        viewModelScope.launch {
            updateState { state ->
                state.copy(loadingState = LoadingState.Loading)
            }
            val exchangeRateResult = knowledgeFeature.getExchangeRateBTCtoCurrency(
                "RUB", "USD", "EUR"
            )

            exchangeRateResult?.let { exchangeRates ->
                val viewItem = ExchangeRateViewItem(
                    abbreviation = PrintableText.Raw(exchangeRates.first().coin),
                    fullCoinName = PrintableText.Raw(exchangeRates.first().coinFullName),
                    iconRes = R.drawable.ic_btc,
                    exchangeRates = ExchangeRate(
                        toRouble = PrintableText.Raw("${exchangeRates[0].value.roundToInt().formatNumber()} ${getCurrencySymbol(exchangeRates[0].currency)}"),
                        toUSD = PrintableText.Raw("${exchangeRates[1].value.roundToInt().formatNumber()} ${getCurrencySymbol(exchangeRates[1].currency)}"),
                        toEUR = PrintableText.Raw("${exchangeRates[2].value.roundToInt().formatNumber()} ${getCurrencySymbol(exchangeRates[2].currency)}")
                    )
                )
                updateState { state ->
                    state.copy(
                        loadingState = LoadingState.Success(data = listOf(viewItem))
                    )
                }
            } ?: updateState { state ->
                state.copy(loadingState = LoadingState.Error(ErrorInfo.createEmpty()))
            }
        }
    }

    private fun Int.formatNumber() : String{
        val number = this.toString()
        return number.reversed().chunked(3).joinToString(separator = " ").reversed()
    }
}