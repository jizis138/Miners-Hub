/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.settings

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.vsibi.btc_mathematic.core.environment.Environment
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.settings_api.SettingsFeature
import ru.vsibi.btc_mathematic.settings_impl.R
import ru.vsibi.btc_mathematic.settings_impl.domain.logic.LocaleManager
import ru.vsibi.btc_mathematic.settings_impl.presentation.about_developer.AboutDeveloperNavigationContract
import ru.vsibi.btc_mathematic.settings_impl.presentation.currency.CurrencyNavigationContract
import ru.vsibi.btc_mathematic.settings_impl.presentation.language.LanguageNavigationContract
import ru.vsibi.btc_mathematic.settings_impl.presentation.settings.model.SettingsViewItem
import ru.vsibi.btc_mathematic.util.PrintableText
import ru.vsibi.btc_mathematic.util.getCurrencyIcon
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
) {

    private val currencyLauncher = launcher(CurrencyNavigationContract) { result ->
        when (result) {
            SettingsFeature.CurrencyResult.Cancel -> Unit
            is SettingsFeature.CurrencyResult.CurrencyChanged -> {
                updateState { state ->
                    state.copy(
                        items = createSettingsList(result.currency)
                    )
                }
            }
        }
    }

    private val languageLauncher = launcher(LanguageNavigationContract) { result ->
        when (result) {
            LanguageNavigationContract.Result.Cancel -> Unit
            is LanguageNavigationContract.Result.LanguageChanged -> {
                viewModelScope.launch {
                    localeManager.changeLocale(result.locale)
                }
            }
        }
    }

    private val aboutDeveloperLauncher = launcher(AboutDeveloperNavigationContract)

    override fun firstState(): SettingsState = SettingsState(
        appVersion = PrintableText.StringResource(R.string.version_description, environment.appVersion, getDateToday()),
        items = createSettingsList(runBlocking { localeManager.getSavedCurrency() })
    )

    private fun createSettingsList(currency: String): List<SettingsViewItem> = listOf(
        SettingsViewItem(
            icon = R.drawable.ic_baseline_language_24,
            title = PrintableText.StringResource(R.string.language),
            onItemClicked = {
                languageLauncher.launch()
            },
            isLocked = false
        ),
        SettingsViewItem(
            icon = getCurrencyIcon(currency),
            title = PrintableText.StringResource(R.string.currency),
            onItemClicked = {
                currencyLauncher.launch()
            },
            isLocked = false
        ),
        SettingsViewItem(
            icon = R.drawable.ic_baseline_android_24,
            title = PrintableText.StringResource(R.string.about_developer),
            onItemClicked = {
                aboutDeveloperLauncher.launch()
            },
            isLocked = false
        )
    )
}

val dateFormatter = SimpleDateFormat("dd.MM.yyyy")

fun getDateToday(): String {
    return dateFormatter.format(Date.from(Instant.now()))
}