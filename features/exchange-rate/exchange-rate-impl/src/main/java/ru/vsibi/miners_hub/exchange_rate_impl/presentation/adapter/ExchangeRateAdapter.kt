package ru.vsibi.miners_hub.exchange_rate_impl.presentation.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.exchange_rate_impl.databinding.HolderExchangeRateBinding
import ru.vsibi.miners_hub.exchange_rate_impl.presentation.model.ExchangeRateViewItem
import ru.vsibi.miners_hub.util.AdapterUtil
import ru.vsibi.miners_hub.util.adapterDelegateViewBinding
import ru.vsibi.miners_hub.util.bindWithBinding
import ru.vsibi.miners_hub.util.setPrintableText

class ExchangeRateAdapter() :
    AsyncListDifferDelegationAdapter<ExchangeRateViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createExchangeRateDelegate(),
        )
    )

fun createExchangeRateDelegate() =
    adapterDelegateViewBinding<ExchangeRateViewItem,
            HolderExchangeRateBinding>(
        HolderExchangeRateBinding::inflate,
    ) {
        bindWithBinding {
            icon.setImageResource(item.iconRes)
            abbreviation.setPrintableText(item.abbreviation)
            fullCoinName.setPrintableText(item.fullCoinName)
            toRouble.setPrintableText(item.exchangeRates.toRouble)
        }
    }
