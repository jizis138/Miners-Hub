/**
 * Created by Dmitry Popov on 14.01.2023.
 */
package ru.vsibi.btc_mathematic.settings_impl.presentation.language

import kotlinx.coroutines.runBlocking
import ru.vsibi.btc_mathematic.mvi.BaseViewModel
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.navigation.model.RequestParams
import ru.vsibi.btc_mathematic.settings_impl.R
import ru.vsibi.btc_mathematic.settings_impl.domain.logic.LocaleManager
import ru.vsibi.btc_mathematic.settings_impl.presentation.language.model.LanguageViewItem
import ru.vsibi.btc_mathematic.util.PrintableText

class LanguageViewModel(
    rootRouter: RootRouter,
    requestParams: RequestParams,
    private val localeManager: LocaleManager
) : BaseViewModel<LanguageState, Nothing>(rootRouter, requestParams) {

    override fun firstState(): LanguageState {
        val selectedLang = runBlocking { localeManager.getSavedLocale() }
        return LanguageState(
            listOf(
                LanguageViewItem(
                    localeName = "en",
                    title = PrintableText.StringResource(R.string.lang_en),
                    isSelected = selectedLang == "en"
                ),
                LanguageViewItem(
                    localeName = "ru",
                    title = PrintableText.StringResource(R.string.lang_ru),
                    isSelected = selectedLang == "ru"
                ),
            )
        )
    }

    fun onLocaleClicked(languageViewItem: LanguageViewItem) {
        exitWithResult(
            LanguageNavigationContract.createResult(
                LanguageNavigationContract.Result.LanguageChanged(
                    languageViewItem.localeName
                )
            )
        )
    }

}