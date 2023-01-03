/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderIncomeModeBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderKnowledgeBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.model.IncomeModeViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.main.model.KnowledgeViewItem
import ru.vsibi.miners_hub.util.*

class IncomeModeAdapter : AsyncListDifferDelegationAdapter<IncomeModeViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createSearchDeviceDelegate()
    )
)

fun createSearchDeviceDelegate() =
    adapterDelegateViewBinding<IncomeModeViewItem,
            HolderIncomeModeBinding>(
        HolderIncomeModeBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.title)
            description.setPrintableTextOrGone(item.description)
            root.onClick{
                item.onClicked()
            }
        }
    }

