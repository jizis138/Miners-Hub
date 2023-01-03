/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.adapter

import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderIncomeModeBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderMinerSelectionBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.model.MinerSelectionViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.model.IncomeModeViewItem
import ru.vsibi.miners_hub.util.*

class MinerSelectionAdapter(onItemClicked : (item : MinerSelectionViewItem) -> Unit) : AsyncListDifferDelegationAdapter<MinerSelectionViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(
        MinerSelectionViewItem::isSelected
    ),
    AdapterUtil.adapterDelegatesManager(
        createMinerSelectionDelegate(onItemClicked)
    )
)

fun createMinerSelectionDelegate(onItemClicked : (item : MinerSelectionViewItem) -> Unit) =
    adapterDelegateViewBinding<MinerSelectionViewItem,
            HolderMinerSelectionBinding>(
        HolderMinerSelectionBinding::inflate,
    ) {
        binding.root.onClick {
            onItemClicked(item)
        }
        bindWithBinding {
            title.setPrintableText(item.name)
            description.setPrintableTextOrGone(item.hashrate)
            root.setBackgroundResource(item.backgroundRes)
            check.isVisible = item.isSelected
        }
    }

