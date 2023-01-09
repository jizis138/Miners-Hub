package ru.vsibi.btc_mathematic.exchange_rate_impl.presentation

import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.exchange_rate_impl.R
import ru.vsibi.btc_mathematic.exchange_rate_impl.databinding.FragmentExchangeRateBinding
import ru.vsibi.btc_mathematic.exchange_rate_impl.presentation.adapter.ExchangeRateAdapter
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.noEventsExpected
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.uikit.renderLoadingState

class ExchangeRateFragment :
    BaseFragment<ExchangeRateState, ExchangeRateEvent>(R.layout.fragment_exchange_rate) {

    override val vm: ExchangeRateViewModel by viewModel()

    private val binding by fragmentViewBinding(FragmentExchangeRateBinding::bind)

    private val adapter = ExchangeRateAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = adapter
    }

    override fun onUpdateState(state: ExchangeRateState) {
        renderLoadingState(
            loadingState = state.loadingState,
            progressContainer = binding.progressContainer,
            errorContainer = binding.errorContainer.errorView,
            contentContainer = binding.list,
            emptyContainer = null,
            renderData = {
                adapter.items = it
            },
            onRetryClicked = {
                vm.loadExchangeRates()
            },
        )
    }

    override fun onRecieveEvent(event: ExchangeRateEvent) = noEventsExpected()

}