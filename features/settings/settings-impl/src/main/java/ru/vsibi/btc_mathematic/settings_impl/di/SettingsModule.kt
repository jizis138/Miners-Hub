/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.settings_impl.di

import org.koin.dsl.module
import ru.vsibi.btc_mathematic.settings_api.SettingsFeature
import ru.vsibi.btc_mathematic.settings_impl.SettingsFeatureImpl
import ru.vsibi.btc_mathematic.di.bindSafe
import ru.vsibi.btc_mathematic.di.factory
import ru.vsibi.btc_mathematic.di.viewModel
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
    }

    private fun createDomainModule() = module {
    }

    private fun createPresentationModule() = module {
        viewModel(::SettingsViewModel)
    }
}