/**
 * Created by Dmitry Popov on 13.12.2022.
 */
package ru.vsibi.miners_hub.knowledge_impl.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vsibi.miners_hub.data.di.qualifier.StorageQualifier
import ru.vsibi.miners_hub.knowledge_api.KnowledgeFeature
import ru.vsibi.miners_hub.knowledge_impl.KnowledgeFeatureImpl
import ru.vsibi.miners_hub.di.bindSafe
import ru.vsibi.miners_hub.di.factory
import ru.vsibi.miners_hub.di.single
import ru.vsibi.miners_hub.di.viewModel
import ru.vsibi.miners_hub.knowledge_impl.data.repo_impl.MinerRepoImpl
import ru.vsibi.miners_hub.knowledge_impl.data.service.MinerService
import ru.vsibi.miners_hub.knowledge_impl.data.storage.MinerStorage
import ru.vsibi.miners_hub.knowledge_impl.domain.logic.MinerInteractor
import ru.vsibi.miners_hub.knowledge_impl.domain.repo.MinerRepository
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_miner.MinerSelectionViewModel
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_mode.IncomeModeViewModel
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.IncomePropertiesViewModel
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.choose_properties.mapper.MinerMapper
import ru.vsibi.miners_hub.knowledge_impl.presentation.calc_income.total.TotalViewModel
import ru.vsibi.miners_hub.knowledge_impl.presentation.main.KnowledgeViewModel
import ru.vsibi.miners_hub.network.di.NetworkClientFactoryQualifier
import ru.vsibi.miners_hub.network.di.singleService

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
        single { MinerService(get(named(NetworkClientFactoryQualifier.Unauthorized))) }
        single {
            MinerStorage(
                databaseFactory = get(named(StorageQualifier.Simple)),
            )
        }
    }

    private fun createDomainModule() = module {
        single(::MinerInteractor)
    }

    private fun createPresentationModule() = module {
        viewModel(::KnowledgeViewModel)
        viewModel(::IncomePropertiesViewModel)
        viewModel(::IncomeModeViewModel)
        viewModel(::MinerSelectionViewModel)
        viewModel(::TotalViewModel)

        factory(::MinerMapper)
    }
}