/**
 * Created by Dmitry Popov on 22.05.2022.
 */
package ru.vsibi.btc_mathematic.main_impl.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commitNow
import ru.vsibi.btc_mathematic.main_api.MainFeature
import ru.vsibi.btc_mathematic.main_impl.R
import ru.vsibi.btc_mathematic.main_impl.databinding.FragmentMain2Binding
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.noEventsExpected
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel

class MainFragment : BaseFragment<MainState, MainEvent>(R.layout.fragment_main_2) {

    override val vm: MainViewModel by viewModel()

    private val binding : FragmentMain2Binding by fragmentViewBinding(FragmentMain2Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView.setOnItemSelectedListener { item ->
            val tabType = TabsMapper.mapItemIdToTab(item) ?: return@setOnItemSelectedListener false
            vm.onShowFragment(tabType)
            true
        }
    }

    override fun onUpdateState(state: MainState) {
        val tabToOpen = state.currentTab
        val tabIdToOpen = when(tabToOpen){
            MainFeature.TabType.Rates -> R.id.rates
            MainFeature.TabType.Mining -> R.id.mining
            MainFeature.TabType.Settings -> R.id.settings
            MainFeature.TabType.Knowledge -> R.id.knowledge
        }

        if(binding.bottomNavigationView.selectedItemId != tabIdToOpen){
            binding.bottomNavigationView.selectedItemId = tabIdToOpen
        }

        childFragmentManager.commitNow {
            childFragmentManager.fragments
                .filter { it.tag != tabToOpen.name }
                .forEach { detach(it) }

            val existedFragment = childFragmentManager.findFragmentByTag(tabToOpen.name)
            when {
                existedFragment == null -> {
                    val newFragment = vm.buildFragment(tabToOpen)
                    add(binding.childContainer.id, newFragment, tabToOpen.name)
                }
                existedFragment.isDetached -> {
                    attach(existedFragment)
                }
            }
        }
    }

    override fun onRecieveEvent(event: MainEvent) = noEventsExpected()
}