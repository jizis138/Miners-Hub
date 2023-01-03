/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.settings_impl.di

import org.koin.dsl.module
import ru.vsibi.miners_hub.settings_api.SettingsFeature
import ru.vsibi.miners_hub.settings_impl.SettingsFeatureImpl
import ru.vsibi.miners_hub.di.bindSafe
import ru.vsibi.miners_hub.di.factory
import ru.vsibi.miners_hub.di.viewModel
import ru.vsibi.miners_hub.settings_impl.presentation.settings.SettingsViewModel

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
    }

    private fun createDomainModule() = module {
    }

    private fun createPresentationModule() = module {
        viewModel(::SettingsViewModel)
    }
}