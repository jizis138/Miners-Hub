package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.create_universal_miner.adapter

import androidx.core.widget.doAfterTextChanged
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderCreateMinerBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.adapter.createUniversalDelegate
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.UniversalMinerViewItem
import ru.vsibi.miners_hub.util.*

class CreateMinerAdapter : AsyncListDifferDelegationAdapter<UniversalMinerViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createUniversalDelegate()
    )
)

fun createUniversalDelegate() = adapterDelegateViewBinding<UniversalMinerViewItem, HolderCreateMinerBinding>(
    HolderCreateMinerBinding::inflate
){
    bindWithBinding {
        minerTitle.setText(item.name)

        plus.onClick {
            item.count += 1
            inputCount.setText(item.count.toString())
        }

        minus.onClick {
            if (item.count == 0) return@onClick
            item.count -= 1
            inputCount.setText(item.count.toString())
        }

        inputPowerKw.setText(item.power.toString())
        inputPowerKw.doAfterTextChanged {
            if (!it.isNullOrEmpty()) {
                item.power = it.toString().toInt()
            } else {
                item.power = 1
            }
        }

        inputPowerTh.setText(item.hashrate.toString())
        inputPowerTh.doAfterTextChanged {
            if (!it.isNullOrEmpty()) {
                item.hashrate = it.toString().toInt()
            } else {
                item.hashrate = 1
            }
        }

        inputCount.setText(item.count.toString())

        minerTitle.doAfterTextChanged {
            it?.let {
                item.name = it.toString()
            }
        }
    }
}