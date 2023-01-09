/**
 * Created by Dmitry Popov on 05.01.2023.
 */
package ru.vsibi.btc_mathematic.exchange_rate_impl.di

import org.koin.dsl.module
import ru.vsibi.btc_mathematic.exchange_rate_api.ExchangeRateFeature
import ru.vsibi.btc_mathematic.exchange_rate_impl.ExchangeRateFeatureImpl
import ru.vsibi.btc_mathematic.di.bindSafe
import ru.vsibi.btc_mathematic.di.factory
import ru.vsibi.btc_mathematic.di.viewModel
import ru.vsibi.btc_mathematic.exchange_rate_impl.presentation.ExchangeRateViewModel

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