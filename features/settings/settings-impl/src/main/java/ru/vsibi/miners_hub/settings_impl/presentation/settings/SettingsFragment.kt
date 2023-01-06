/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.settings_impl.presentation.settings

import android.os.Bundle
import android.view.View
import org.koin.android.BuildConfig
import ru.vsibi.miners_hub.presentation.base.ui.BaseFragment
import ru.vsibi.miners_hub.presentation.base.util.fragmentViewBinding
import ru.vsibi.miners_hub.presentation.base.util.noEventsExpected
import ru.vsibi.miners_hub.presentation.base.util.nothingToUpdate
import ru.vsibi.miners_hub.presentation.base.util.viewModel
import ru.vsibi.miners_hub.settings_impl.R
import ru.vsibi.miners_hub.settings_impl.databinding.FragmentSettingsBinding
import ru.vsibi.miners_hub.util.setPrintableText

class SettingsFragment : BaseFragment<SettingsState, SettingsEvent>(R.layout.fragment_settings) {

    override val vm : SettingsViewModel by viewModel()

    private val binding by fragmentViewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)
        appName.text = getString(ru.vsibi.miners_hub.core.R.string.app_name)
    }

    override fun onUpdateState(state: SettingsState) {
        binding.version.setPrintableText(state.appVersion)
    }

    override fun onRecieveEvent(event: SettingsEvent) = noEventsExpected()
}