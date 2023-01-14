/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.settings

import android.provider.Settings
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vsibi.btc_mathematic.core.environment.Environment
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.settings_impl.R
import ru.vsibi.btc_mathematic.settings_impl.domain.logic.LocaleManager
import ru.vsibi.btc_mathematic.settings_impl.presentation.language.LanguageNavigationContract
import ru.vsibi.btc_mathematic.settings_impl.presentation.settings.model.SettingsViewItem
import ru.vsibi.btc_mathematic.util.PrintableText
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class SettingsViewModel(
    requestParams: RequestParams,
    rootRouter: RootRouter,
    private val environment: Environment,
    private val localeManager: LocaleManager
) : BaseViewModel<SettingsState, SettingsEvent>(
    rootRouter, requestParams
){

    private val languageLauncher = launcher(LanguageNavigationContract){ result->
        when(result){
            LanguageNavigationContract.Result.Cancel -> Unit
            is LanguageNavigationContract.Result.LanguageChanged -> {
                viewModelScope.launch {
                    localeManager.changeLocale(result.locale)
                }
            }
        }
    }

    override fun firstState(): SettingsState = SettingsState(
        appVersion = PrintableText.StringResource(R.string.version_description, environment.appVersion, getDateToday()),
        items = listOf(
            SettingsViewItem(
                icon = R.drawable.ic_baseline_language_24,
                title = PrintableText.StringResource(R.string.language),
                onItemClicked = {
                    languageLauncher.launch()
                },
                isLocked = false
            )
        )
    )

}

val dateFormatter = SimpleDateFormat("dd.MM.yyyy")

fun getDateToday(): String {
    return dateFormatter.format(Date.from(Instant.now()))
}