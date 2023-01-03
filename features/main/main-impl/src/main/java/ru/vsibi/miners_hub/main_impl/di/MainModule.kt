/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.miners_hub.main_impl.di

import org.koin.dsl.module
import ru.vsibi.miners_hub.di.bindSafe
import ru.vsibi.miners_hub.di.factory
import ru.vsibi.miners_hub.di.single
import ru.vsibi.miners_hub.di.viewModel
import ru.vsibi.miners_hub.main_api.DeepLinkNavigatorImpl
import ru.vsibi.miners_hub.main_api.MainFeature
import ru.vsibi.miners_hub.main_impl.MainFeatureImpl
import ru.vsibi.miners_hub.main_impl.domain.logic.OuterTabNavigator
import ru.vsibi.miners_hub.main_impl.presentation.main.MainViewModel
import ru.vsibi.miners_hub.navigation.model.DeepLinkNavigator

object MainModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory(::MainFeatureImpl) bindSafe MainFeature::class

        factory(::DeepLinkNavigatorImpl) bindSafe DeepLinkNavigator::class
    }

    private fun createDataModule() = module {
    }

    private fun createDomainModule() = module {
        single(::OuterTabNavigator)
    }

    private fun createPresentationModule() = module {
        viewModel(::MainViewModel)

        factory(MainViewModel::Features)
    }
}