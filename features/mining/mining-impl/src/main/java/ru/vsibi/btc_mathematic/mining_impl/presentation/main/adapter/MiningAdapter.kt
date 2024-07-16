package ru.vsibi.btc_mathematic.mining_impl.presentation.main.adapter

import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.mining_impl.databinding.HolderFarmBinding
import ru.vsibi.btc_mathematic.mining_impl.presentation.main.model.FarmViewItem
import ru.vsibi.btc_mathematic.util.*

class MiningAdapter(
    onDetailClicked: (FarmViewItem) -> Unit,
    onMenuClicked: (FarmViewItem) -> Unit
) :
    AsyncListDifferDelegationAdapter<FarmViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createMiningDelegate(onDetailClicked, onMenuClicked)
        )
    )

fun createMiningDelegate(
    onDetailClicked: (FarmViewItem) -> Unit,
    onMenuClicked: (FarmViewItem) -> Unit
) =
    adapterDelegateViewBinding<FarmViewItem, HolderFarmBinding>(
        HolderFarmBinding::inflate
    ) {
        binding.root.onClick {
            onDetailClicked(item)
        }
        binding.menu.onClick {
            onMenuClicked(item)
        }
        bindWithBinding {
            menu.increaseHitArea(16.dp)

            iconVia.isVisible = item.usingViaBtc
            title.setPrintableText(item.title)
            power.setPrintableText(item.totalPower)
            incomePerDay.setPrintableText(item.incomePerDay)
            incomePerMonth.setPrintableText(item.incomePerMonth)
        }

    }
