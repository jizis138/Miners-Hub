/**
 * Created by Dmitry Popov on 22.05.2022.
 */
package ru.vsibi.momento.main_impl.presentation.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commitNow
import ru.vsibi.momento.main_impl.R
import ru.vsibi.momento.main_impl.databinding.FragmentMainBinding
import ru.vsibi.momento.presentation.base.ui.BaseFragment
import ru.vsibi.momento.presentation.base.util.fragmentViewBinding
import ru.vsibi.momento.presentation.base.util.noEventsExpected
import ru.vsibi.momento.presentation.base.util.viewModel
import ru.vsibi.momento.util.onClick

class MainFragment : BaseFragment<MainState, MainEvent>(R.layout.fragment_main) {

    override val vm: MainViewModel by viewModel()

    private val binding : FragmentMainBinding by fragmentViewBinding(FragmentMainBinding::bind)

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

        binding.bottomNavigationView.selectedItemId = TabsMapper.mapTabToItemId(tabToOpen)

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