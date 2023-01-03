/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderPropertiesChooseMinerBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderResultItemBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderTotalResultsBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.adapter.MinersAdapter
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.ResultViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.TotalViewItem
import ru.vsibi.miners_hub.util.*

class TotalAdapter : AsyncListDifferDelegationAdapter<TotalViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createResultsDelegate(),
    )
)

fun createResultsDelegate() =
    adapterDelegateViewBinding<TotalViewItem.Results,
            HolderTotalResultsBinding>(
        HolderTotalResultsBinding::inflate,
    ) {
        bindWithBinding {
            val adapter = ResultsAdapter()
            resultList.adapter = adapter

            adapter.items = item.items
        }
    }

class ResultsAdapter : AsyncListDifferDelegationAdapter<ResultViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createResultsItemsDelegate(),
    )
)

fun createResultsItemsDelegate() =
    adapterDelegateViewBinding<ResultViewItem,
            HolderResultItemBinding>(
        HolderResultItemBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.title)
            description.setPrintableText(item.totalValue)
        }
    }

