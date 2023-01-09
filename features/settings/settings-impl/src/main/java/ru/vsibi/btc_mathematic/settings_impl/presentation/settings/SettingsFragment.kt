/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.settings

import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.noEventsExpected
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.settings_impl.R
import ru.vsibi.btc_mathematic.settings_impl.databinding.FragmentSettingsBinding
import ru.vsibi.btc_mathematic.util.setPrintableText

class SettingsFragment : BaseFragment<SettingsState, SettingsEvent>(R.layout.fragment_settings) {

    override val vm : SettingsViewModel by viewModel()

    private val binding by fragmentViewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)
        appName.text = getString(ru.vsibi.btc_mathematic.core.R.string.app_name)
    }

    override fun onUpdateState(state: SettingsState) {
        binding.version.setPrintableText(state.appVersion)
    }

    override fun onRecieveEvent(event: SettingsEvent) = noEventsExpected()
}