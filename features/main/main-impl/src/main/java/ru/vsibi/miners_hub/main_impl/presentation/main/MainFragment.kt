/**
 * Created by Dmitry Popov on 22.05.2022.
 */
package ru.vsibi.miners_hub.main_impl.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commitNow
import ru.vsibi.miners_hub.main_impl.R
import ru.vsibi.miners_hub.main_impl.databinding.FragmentMain2Binding
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.fragmentViewBinding
import ru.vsibi.miners_hub.presentation.base.util.noEventsExpected
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.util.onClick

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