/**
 * Created by Dmitry Popov on 15.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.about_developer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import ru.vsibi.btc_mathematic.presentation.base.ui.BaseFragment
import ru.vsibi.btc_mathematic.presentation.base.util.fragmentViewBinding
import ru.vsibi.btc_mathematic.presentation.base.util.noEventsExpected
import ru.vsibi.btc_mathematic.presentation.base.util.nothingToUpdate
import ru.vsibi.btc_mathematic.presentation.base.util.viewModel
import ru.vsibi.btc_mathematic.settings_impl.R
import ru.vsibi.btc_mathematic.settings_impl.databinding.FragmentAboutDeveloperBinding
import ru.vsibi.btc_mathematic.util.onClick


class AboutDeveloperFragment :
    BaseFragment<AboutDeveloperState, AboutDeveloperEvent>(R.layout.fragment_about_developer) {

    override val vm: AboutDeveloperViewModel by viewModel()

    private val binding by fragmentViewBinding(FragmentAboutDeveloperBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        cancel.onClick {
            requireActivity().onBackPressed()
        }
        sendEmail.onClick {
            val emails: Array<String> = arrayOf("vsibiriteplo@yandex.ru")
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, emails)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject_email))
            requireContext().startActivity(
                Intent.createChooser(
                    emailIntent,
                    "Send email using..."
                )
            )
        }
    }

    override fun onUpdateState(state: AboutDeveloperState) = nothingToUpdate()

    override fun onRecieveEvent(event: AboutDeveloperEvent) = noEventsExpected()

}