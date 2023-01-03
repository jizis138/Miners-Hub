/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.settings_impl.presentation.settings

import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.noEventsExpected
import ru.vsibi.miners_hub.presentation.base.util.nothingToUpdate
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.settings_impl.R

class SettingsFragment : BaseFragment<SettingsState, SettingsEvent>(R.layout.fragment_settings) {

    override val vm : SettingsViewModel by viewModel()

    override fun onUpdateState(state: SettingsState) = nothingToUpdate()

    override fun onRecieveEvent(event: SettingsEvent) = noEventsExpected()
}