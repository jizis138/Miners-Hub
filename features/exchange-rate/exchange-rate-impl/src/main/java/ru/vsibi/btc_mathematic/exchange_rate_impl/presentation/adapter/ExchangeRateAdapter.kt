package ru.vsibi.btc_mathematic.exchange_rate_impl.presentation.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.exchange_rate_impl.databinding.HolderExchangeRateBinding
import ru.vsibi.btc_mathematic.exchange_rate_impl.presentation.model.ExchangeRateViewItem
import ru.vsibi.btc_mathematic.util.AdapterUtil
import ru.vsibi.btc_mathematic.util.adapterDelegateViewBinding
import ru.vsibi.btc_mathematic.util.bindWithBinding
import ru.vsibi.btc_mathematic.util.setPrintableText

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
