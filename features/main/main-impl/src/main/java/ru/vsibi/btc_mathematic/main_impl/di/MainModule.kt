/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.btc_mathematic.main_impl.di

import org.koin.dsl.module
import ru.vsibi.btc_mathematic.di.bindSafe
import ru.vsibi.btc_mathematic.di.factory
import ru.vsibi.btc_mathematic.di.single
import ru.vsibi.btc_mathematic.di.viewModel
import ru.vsibi.btc_mathematic.main_api.DeepLinkNavigatorImpl
import ru.vsibi.btc_mathematic.main_api.MainFeature
import ru.vsibi.btc_mathematic.main_impl.MainFeatureImpl
import ru.vsibi.btc_mathematic.main_impl.domain.logic.OuterTabNavigator
import ru.vsibi.btc_mathematic.main_impl.presentation.main.MainViewModel
import ru.vsibi.btc_mathematic.navigation.model.DeepLinkNavigator

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