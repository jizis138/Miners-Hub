/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.settings_impl.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vsibi.btc_mathematic.data.di.qualifier.StorageQualifier
import ru.vsibi.btc_mathematic.di.bindSafe
import ru.vsibi.btc_mathematic.di.factory
import ru.vsibi.btc_mathematic.di.viewModel
import ru.vsibi.btc_mathematic.settings_api.SettingsFeature
import ru.vsibi.btc_mathematic.settings_impl.SettingsFeatureImpl
import ru.vsibi.btc_mathematic.settings_impl.data.storage.LocaleStorage
import ru.vsibi.btc_mathematic.settings_impl.domain.logic.LocaleManager
import ru.vsibi.btc_mathematic.settings_impl.presentation.about_developer.AboutDeveloperViewModel
import ru.vsibi.btc_mathematic.settings_impl.presentation.currency.CurrencyViewModel
import ru.vsibi.btc_mathematic.settings_impl.presentation.language.LanguageViewModel
import ru.vsibi.btc_mathematic.settings_impl.presentation.settings.LocaleManagerImpl
import ru.vsibi.btc_mathematic.settings_impl.presentation.settings.SettingsViewModel

object SettingsModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory(::SettingsFeatureImpl) bindSafe SettingsFeature::class
    }

    private fun createDataModule() = module {
        single { LocaleStorage(get(named(StorageQualifier.Simple))) }
    }

    private fun createDomainModule() = module {
    }

    private fun createPresentationModule() = module {
        viewModel(::SettingsViewModel)
        viewModel(::CurrencyViewModel)
        viewModel(::LanguageViewModel)
        viewModel(::AboutDeveloperViewModel)

        single { LocaleManagerImpl(get()) } bindSafe LocaleManager::class
    }
}