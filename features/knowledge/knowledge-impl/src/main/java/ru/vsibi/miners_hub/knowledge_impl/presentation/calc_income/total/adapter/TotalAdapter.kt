/**
 * Created by Dmitry Popov on 15.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.adapter

import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderDetailsItemBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderPropertiesChooseMinerBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderResultItemBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderShareCalculationBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderTotalDetailsBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderTotalResultsBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.adapter.MinersAdapter
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.DetailViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.ResultViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.model.TotalViewItem
import ru.vsibi.miners_hub.util.*

class TotalAdapter(onExpandClicked: () -> Unit, onShareClicked: () -> Unit) :
    AsyncListDifferDelegationAdapter<TotalViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createResultsDelegate(),
            createDetailsDelegate(onExpandClicked),
            createShareDelegate(onShareClicked)
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

fun createDetailsDelegate(onExpandClicked: () -> Unit) =
    adapterDelegateViewBinding<TotalViewItem.Details,
            HolderTotalDetailsBinding>(
        HolderTotalDetailsBinding::inflate,
    ) {
        binding.expand.onClick {
            item.expanded = !item.expanded
            onExpandClicked()
        }
        bindWithBinding {
            detailsList.isVisible = item.expanded
            expand.setImageResource(item.expandedRes)

            val adapter = DetailsAdapter()
            detailsList.adapter = adapter

            adapter.items = item.items
        }
    }

fun createShareDelegate(onShareClicked: () -> Unit) =
    adapterDelegateViewBinding<TotalViewItem.ShareCalculation,
            HolderShareCalculationBinding>(
        HolderShareCalculationBinding::inflate,
    ) {
        binding.root.onClick {
            onShareClicked()
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

class DetailsAdapter : AsyncListDifferDelegationAdapter<DetailViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createDetailsItemsDelegate(),
    )
)

fun createDetailsItemsDelegate() =
    adapterDelegateViewBinding<DetailViewItem,
            HolderDetailsItemBinding>(
        HolderDetailsItemBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.title)
            description.setPrintableText(item.description)
        }
    }

