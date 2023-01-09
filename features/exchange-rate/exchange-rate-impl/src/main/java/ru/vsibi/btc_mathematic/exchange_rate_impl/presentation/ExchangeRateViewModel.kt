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
            val exchangeRateResult = knowledgeFeature.getExchangeRateBTCtoRouble()
            exchangeRateResult?.let { exchangeRate ->
                val viewItem = ExchangeRateViewItem(
                    abbreviation = PrintableText.Raw(exchangeRate.coin),
                    fullCoinName = PrintableText.Raw(exchangeRate.coinFullName),
                    iconRes = R.drawable.ic_btc,
                    exchangeRates = ExchangeRate(
                        toRouble = PrintableText.Raw("${exchangeRate.value} ${exchangeRate.currency}")
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
}