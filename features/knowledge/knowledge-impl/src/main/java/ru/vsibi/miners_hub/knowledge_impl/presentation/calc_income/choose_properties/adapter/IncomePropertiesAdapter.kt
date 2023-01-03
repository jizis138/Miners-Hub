/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.adapter

import androidx.core.widget.doAfterTextChanged
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderElectricityBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderMinerPropertyBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderMinerSelectionBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderMinerUniversalPropertyBinding
import ru.vsibi.miners_hub.knowledge_impl.databinding.HolderPropertiesChooseMinerBinding
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.IncomePropertiesViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.MinerViewItem
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.model.UniversalMinerViewItem
import ru.vsibi.miners_hub.util.*

class IncomePropertiesAdapter(onRemoveClicked: (UniversalMinerViewItem) -> Unit) :
    AsyncListDifferDelegationAdapter<IncomePropertiesViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createChooseMinerDelegate(),
            createUniversalDelegate(onRemoveClicked),
            createElectricityDelegate()
        )
    )

fun createChooseMinerDelegate() =
    adapterDelegateViewBinding<IncomePropertiesViewItem.MinerSelection,
            HolderPropertiesChooseMinerBinding>(
        HolderPropertiesChooseMinerBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.title)

            root.onClick {
                item.onClicked()
            }

            val adapter = MinersAdapter()
            minersList.adapter = adapter

            adapter.items = item.items
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

fun createElectricityDelegate() =
    adapterDelegateViewBinding<IncomePropertiesViewItem.ElectricitySelection,
            HolderElectricityBinding>(
        HolderElectricityBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.title)

            count.doAfterTextChanged {
                item.electricityPrice = it.toString().toDouble()
            }
        }
    }

class MinersAdapter : AsyncListDifferDelegationAdapter<MinerViewItem>(
    AdapterUtil.diffUtilItemCallbackEquals(),
    AdapterUtil.adapterDelegatesManager(
        createMinerDelegate()
    )
)

fun createMinerDelegate() =
    adapterDelegateViewBinding<MinerViewItem,
            HolderMinerPropertyBinding>(
        HolderMinerPropertyBinding::inflate,
    ) {
        bindWithBinding {
            title.setPrintableText(item.name)
            description.setPrintableText(item.hashrate)

            plus.onClick {
                item.count += 1
                count.setText(item.count.toString())
            }

            minus.onClick {
                if (item.count == 0) return@onClick
                item.count -= 1
                count.setText(item.count.toString())
            }

            count.setText(item.count.toString())
            count.doAfterTextChanged {
                if (!it.isNullOrEmpty()) {
                    item.count = it.toString().toInt()
                } else {
                    item.count = 1
                }
            }
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
