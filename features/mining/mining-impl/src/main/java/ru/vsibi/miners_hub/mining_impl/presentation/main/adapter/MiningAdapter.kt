package ru.vsibi.miners_hub.mining_impl.presentation.main.adapter

import android.view.View
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.knowledge_api.model.Farm
import ru.vsibi.miners_hub.mining_impl.databinding.HolderFarmBinding
import ru.vsibi.miners_hub.mining_impl.presentation.main.model.FarmViewItem
import ru.vsibi.miners_hub.util.*

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
        binding.details.onClick {
            onDetailClicked(item)
        }
        binding.menu.onClick {
            onMenuClicked(item)
        }
        bindWithBinding {
            menu.increaseHitArea(16.dp)

            title.setPrintableText(item.title)
            power.setPrintableText(item.totalPower)
            incomePerDay.setPrintableText(item.incomePerDay)
            incomePerMonth.setPrintableText(item.incomePerMonth)
        }

    }
