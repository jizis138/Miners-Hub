package ru.vsibi.btc_mathematic.exchange_rate_impl.presentation.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.exchange_rate_impl.databinding.HolderRateBinding
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
            HolderRateBinding>(
        HolderRateBinding::inflate,
    ) {
        bindWithBinding {
            icon.setImageResource(item.iconRes)
            sampleName.setPrintableText(item.abbreviation)
            fullName.setPrintableText(item.fullCoinName)
            toRouble.setPrintableText(item.exchangeRates.toRouble)
            toEur.setPrintableText(item.exchangeRates.toEUR)
            toUsd.setPrintableText(item.exchangeRates.toUSD)
        }
    }
