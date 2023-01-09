/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_miner.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.HolderMinerSelectionBinding
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_miner.model.MinerSelectionViewItem
import ru.vsibi.btc_mathematic.util.*

class MinerSelectionAdapter(onItemClicked: (item: MinerSelectionViewItem) -> Unit) :
    AsyncListDifferDelegationAdapter<MinerSelectionViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(
            MinerSelectionViewItem::isSelected
        ),
        AdapterUtil.adapterDelegatesManager(
            createMinerSelectionDelegate(onItemClicked)
        )
    )

fun createMinerSelectionDelegate(onItemClicked: (item: MinerSelectionViewItem) -> Unit) =
    adapterDelegateViewBinding<MinerSelectionViewItem,
            HolderMinerSelectionBinding>(
        HolderMinerSelectionBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.name)
            description.setPrintableTextOrGone(item.hashrate)
            root.setBackgroundResource(item.backgroundRes)

            plus.onClick {
                item.count += 1
                count.setText(item.count.toString())
                onItemClicked(item)
            }

            minus.onClick {
                if (item.count == 0) return@onClick
                item.count -= 1
                count.setText(item.count.toString())
                onItemClicked(item)
            }

            count.setText(item.count.toString())
        }
    }

