/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.adapter

import androidx.core.widget.doAfterTextChanged
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.HolderElectricityBinding
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.HolderMinerPropertyBinding
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.HolderMinerUniversalPropertyBinding
import ru.vsibi.btc_mathematic.knowledge_impl.databinding.HolderPropertiesChooseMinerBinding
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model.MinerViewItem
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.model.UniversalMinerViewItem
import ru.vsibi.btc_mathematic.util.*


class IncomePropertiesAdapter(
    minersAdapter: MinersAdapter,
    onElectricityPriceChanged: (IncomePropertiesViewItem.ElectricitySelection) -> Unit
) :
    AsyncListDifferDelegationAdapter<IncomePropertiesViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createChooseMinerDelegate(minersAdapter),
            createElectricityDelegate(onElectricityPriceChanged)
        )
    )

fun createChooseMinerDelegate(minersAdapter: MinersAdapter) =
    adapterDelegateViewBinding<IncomePropertiesViewItem.MinerSelection,
            HolderPropertiesChooseMinerBinding>(
        HolderPropertiesChooseMinerBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.title)

            root.onClick {
                item.onClicked()
            }
            minersList.adapter = minersAdapter
            minersAdapter.items = item.items
        }
    }

fun createUniversalDelegate(onRemoveClicked: (UniversalMinerViewItem) -> Unit) =
    adapterDelegateViewBinding<IncomePropertiesViewItem.UniversalSelection,
            HolderPropertiesChooseMinerBinding>(
        HolderPropertiesChooseMinerBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.title)

            root.onClick {
                item.onClicked()
            }

            val adapter = UniversalMinersAdapter(onRemoveClicked)
            minersList.adapter = adapter

            adapter.items = item.items
        }
    }

fun createElectricityDelegate(onElectricityPriceChanged: (IncomePropertiesViewItem.ElectricitySelection) -> Unit) =
    adapterDelegateViewBinding<IncomePropertiesViewItem.ElectricitySelection,
            HolderElectricityBinding>(
        HolderElectricityBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.title)

            if (item.electricityPrice != 0.0) {
                count.setText(item.electricityPrice.toString())
            }
            count.doAfterTextChanged {
                if (!it.isNullOrEmpty()) {
                    item.electricityPrice = it.toString().toDouble()
                } else {
                    item.electricityPrice = 0.0
                }
                onElectricityPriceChanged(item)
            }
        }
    }

class MinersAdapter(
    onRemoveViewClicked: (MinerViewItem) -> Unit,
    onItemCountChanged: (MinerViewItem) -> Unit
) :
    AsyncListDifferDelegationAdapter<MinerViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createMinerDelegate(onRemoveViewClicked, onItemCountChanged)
        )
    ) {

}

fun createMinerDelegate(
    onRemoveClicked: (MinerViewItem) -> Unit,
    onItemCountChanged: (MinerViewItem) -> Unit
) =
    adapterDelegateViewBinding<MinerViewItem,
            HolderMinerPropertyBinding>(
        HolderMinerPropertyBinding::inflate,
    ) {
        binding.remove.onClick {
            onRemoveClicked(item)
        }

        bindWithBinding {
            remove.increaseHitArea(16.dp)
            title.setPrintableText(item.name)
            description.setPrintableText(item.description)

            plus.onClick {
                item.count += 1
                count.setText(item.count.toString())
                onItemCountChanged(item)
            }

            minus.onClick {
                if (item.count == 0) return@onClick
                item.count -= 1
                count.setText(item.count.toString())
                onItemCountChanged(item)
            }

            count.setText(item.count.toString())
        }
    }


class UniversalMinersAdapter(onRemoveClicked: (UniversalMinerViewItem) -> Unit) :
    AsyncListDifferDelegationAdapter<UniversalMinerViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createUniversalMinerDelegate(onRemoveClicked)
        )
    )

fun createUniversalMinerDelegate(
    onRemoveClicked: (UniversalMinerViewItem) -> Unit
) =
    adapterDelegateViewBinding<UniversalMinerViewItem,
            HolderMinerUniversalPropertyBinding>(
        HolderMinerUniversalPropertyBinding::inflate,
    ) {
        binding.remove.onClick {
            onRemoveClicked(item)
        }
        bindWithBinding {
            minerTitle.setPrintableText(item.name)

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
            inputCount.doAfterTextChanged {
                if (!it.isNullOrEmpty()) {
                    item.count = it.toString().toInt()
                } else {
                    item.count = 1
                }
            }
        }
    }