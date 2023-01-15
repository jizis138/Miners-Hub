/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.btc_mathematic.knowledge_impl.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vsibi.btc_mathematic.data.di.qualifier.StorageQualifier
import ru.vsibi.btc_mathematic.knowledge_api.KnowledgeFeature
import ru.vsibi.btc_mathematic.knowledge_impl.KnowledgeFeatureImpl
import ru.vsibi.btc_mathematic.di.bindSafe
import ru.vsibi.btc_mathematic.di.factory
import ru.vsibi.btc_mathematic.di.single
import ru.vsibi.btc_mathematic.di.viewModel
import ru.vsibi.btc_mathematic.knowledge_impl.data.repo_impl.DifficultyRepoImpl
import ru.vsibi.btc_mathematic.knowledge_impl.data.repo_impl.ExchangeRateRepoImpl
import ru.vsibi.btc_mathematic.knowledge_impl.data.repo_impl.HistoryRepoImpl
import ru.vsibi.btc_mathematic.knowledge_impl.data.repo_impl.MinerRepoImpl
import ru.vsibi.btc_mathematic.knowledge_impl.data.service.DifficultyService
import ru.vsibi.btc_mathematic.knowledge_impl.data.service.ExchangeRateService
import ru.vsibi.btc_mathematic.knowledge_impl.data.service.MinerService
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.HistoryStorage
import ru.vsibi.btc_mathematic.knowledge_impl.data.storage.MinerStorage
import ru.vsibi.btc_mathematic.knowledge_impl.domain.logic.CalculationInteractor
import ru.vsibi.btc_mathematic.knowledge_impl.domain.logic.MinerInteractor
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.DifficultyRepository
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.ExchangeRateRepository
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.HistoryRepository
import ru.vsibi.btc_mathematic.knowledge_impl.domain.repo.MinerRepository
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_miner.MinerSelectionViewModel
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_miner.mapper.MinerSelectionMapper
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_mode.IncomeModeViewModel
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.IncomePropertiesViewModel
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.choose_properties.mapper.MinerMapper
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.create_universal_miner.CreateUniversalMinerViewModel
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.history.HistoryViewModel
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.calc_income.total.TotalViewModel
import ru.vsibi.btc_mathematic.knowledge_impl.presentation.main.KnowledgeViewModel
import ru.vsibi.btc_mathematic.network.di.NetworkClientFactoryQualifier

object KnowledgeModule {
    operator fun invoke() = listOf(
        createFeatureModule(),
        createDataModule(),
        createDomainModule(),
        createPresentationModule(),
    )

    private fun createFeatureModule() = module {
        factory(::KnowledgeFeatureImpl) bindSafe KnowledgeFeature::class
    }

    private fun createDataModule() = module {
        single(::MinerRepoImpl) bindSafe MinerRepository::class
        single(::DifficultyRepoImpl) bindSafe DifficultyRepository::class
        single(::ExchangeRateRepoImpl) bindSafe ExchangeRateRepository::class
        single(::HistoryRepoImpl) bindSafe HistoryRepository::class

        single { MinerService(get(named(NetworkClientFactoryQualifier.Unauthorized))) }
        single { DifficultyService(get(named(NetworkClientFactoryQualifier.Unauthorized))) }
        single { ExchangeRateService(get(named(NetworkClientFactoryQualifier.Unauthorized))) }
        single {
            MinerStorage(
                databaseFactory = get(named(StorageQualifier.Simple)),
            )
        }
        single {
            HistoryStorage(
                databaseFactory = get(named(StorageQualifier.Simple)),
            )
        }
    }

    private fun createDomainModule() = module {
        single(::MinerInteractor)
        single(::CalculationInteractor)
    }

    private fun createPresentationModule() = module {
        viewModel(::KnowledgeViewModel)
        viewModel(::IncomePropertiesViewModel)
        viewModel(::IncomeModeViewModel)
        viewModel(::MinerSelectionViewModel)
        viewModel(::TotalViewModel)
        viewModel(::CreateUniversalMinerViewModel)
        viewModel(::HistoryViewModel)

        factory(::MinerMapper)
        factory { MinerSelectionMapper(androidContext()) }
    }
}