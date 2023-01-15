/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.HolderHistoryBinding
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.model.HistoryViewItem
import ru.vsibi.btc_mathematic.util.*

class HistoryAdapter(
    onItemClicked: (HistoryViewItem) -> Unit,
    onMenuClicked: (HistoryViewItem) -> Unit
) : AsyncListDifferDelegationAdapter<HistoryViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createHistoryDelegate(onItemClicked, onMenuClicked)
    )
)

fun createHistoryDelegate(
    onItemClicked: (HistoryViewItem) -> Unit,
    onMenuClicked: (HistoryViewItem) -> Unit
) =
    adapterDelegateViewBinding<HistoryViewItem,
            HolderHistoryBinding>(
        HolderHistoryBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.fromDate)
            power.setPrintableText(item.totalPower)
            incomePerMonth.setPrintableText(item.incomePerMonth)
            composition.setPrintableText(item.composition)

            menu.increaseHitArea(16.dp)
            menu.onClick {
                onMenuClicked(item)
            }
            root.onClick {
                onItemClicked(item)
            }
        }
    }

