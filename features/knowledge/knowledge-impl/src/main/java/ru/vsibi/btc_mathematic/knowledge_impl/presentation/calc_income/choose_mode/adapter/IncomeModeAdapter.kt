/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_mode.adapter

import androidx.core.view.isVisible
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.HolderIncomeModeBinding
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_mode.model.IncomeModeViewItem
import ru.vsibi.btc_mathematic.util.*

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
            lock.isVisible = item.isLocked
        }
    }

