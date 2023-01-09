/**
 * Created by Dmitry Popov on 05.01.2023.
 */
package ru.vsibi.btc_mathematic.mining_impl.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vsibi.btc_mathematic.data.di.qualifier.StorageQualifier
import ru.vsibi.btc_mathematic.mining_api.MiningFeature
import ru.vsibi.btc_mathematic.mining_impl.MiningFeatureImpl
import ru.vsibi.btc_mathematic.di.bindSafe
import ru.vsibi.btc_mathematic.di.factory
import ru.vsibi.btc_mathematic.di.single
import ru.vsibi.btc_mathematic.di.viewModel
import ru.vsibi.btc_mathematic.mining_impl.data.repo_impl.MiningRepositoryImpl
import ru.vsibi.btc_mathematic.mining_impl.data.storage.MiningStorage
import ru.vsibi.btc_mathematic.mining_impl.domain.logic.MiningInteractor
import ru.vsibi.btc_mathematic.mining_impl.domain.repo.MiningRepository
import ru.vsibi.btc_mathematic.mining_impl.presentation.main.MiningViewModel
import ru.vsibi.btc_mathematic.mining_impl.presentation.main.mapper.MiningMapper

object MiningModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory(::MiningFeatureImpl) bindSafe MiningFeature::class
    }

    private fun createDataModule() = module {
        single(::MiningRepositoryImpl) bindSafe MiningRepository::class

        single {
            MiningStorage(
                databaseFactory = get(named(StorageQualifier.Simple)),
            )
        }
    }

    private fun createDomainModule() = module {
        factory(::MiningInteractor)
    }

    private fun createPresentationModule() = module {
        viewModel(::MiningViewModel)

        factory(::MiningMapper)
    }
}