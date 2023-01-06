/**
 * Created by Dmitry Popov on 05.01.2023.
 */
package ru.vsibi.miners_hub.exchange_rate_impl.di

import org.koin.dsl.module
import ru.vsibi.miners_hub.exchange_rate_api.ExchangeRateFeature
import ru.vsibi.miners_hub.exchange_rate_impl.ExchangeRateFeatureImpl
import ru.vsibi.miners_hub.di.bindSafe
import ru.vsibi.miners_hub.di.factory
import ru.vsibi.miners_hub.di.single
import ru.vsibi.miners_hub.di.viewModel
import ru.vsibi.miners_hub.exchange_rate_impl.presentation.ExchangeRateViewModel

object ExchangeRateModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory(::ExchangeRateFeatureImpl) bindSafe ExchangeRateFeature::class
    }

    private fun createDataModule() = module {
    }

    private fun createDomainModule() = module {
    }

    private fun createPresentationModule() = module {
        viewModel(::ExchangeRateViewModel)
    }
}