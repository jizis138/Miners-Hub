/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.main.adapter

import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderKnowledgeBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.main.model.KnowledgeViewItem
import ru.vsibi.miners_hub.util.*

class KnowledgeAdapter : AsyncListDifferDelegationAdapter<KnowledgeViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createSearchDeviceDelegate()
    )
)

fun createSearchDeviceDelegate() =
    adapterDelegateViewBinding<KnowledgeViewItem,
            HolderKnowledgeBinding>(
        HolderKnowledgeBinding::inflate,
    ) {
        bindWithBinding {
            with(item){
                icon.setImageResource(iconRes)
                title.setPrintableText(titleText)
                root.onClick(onClicked)

                lock.isVisible = item.isLocked

            }
        }
    }

