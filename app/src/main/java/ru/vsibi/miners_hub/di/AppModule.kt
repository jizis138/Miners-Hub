package ru.vsibi.miners_hub.di

import org.koin.core.module.Module
import ru.vsibi.miners_hub.data.di.DataModule
import ru.vsibi.miners_hub.exchange_rate_impl.di.ExchangeRateModule
import ru.vsibi.miners_hub.knowledge_impl.di.KnowledgeModule
import ru.vsibi.miners_hub.main_impl.di.MainModule
import ru.vsibi.miners_hub.mining_impl.di.MiningModule
import ru.vsibi.miners_hub.network.di.NetworkModule
import ru.vsibi.miners_hub.notes_impl.di.NotesModule
import ru.vsibi.miners_hub.settings_impl.di.SettingsModule


object AppModule {

    operator fun invoke() = listOf(
        createFeatureModules(),
        PresentationModule(),
        listOf(EnvironmentModule()),
        NetworkModule(),
        DataModule(),
//        listOf(ComponentModule())
    ).flatten()

    private fun createFeatureModules(): List<Module> = listOf(
        MainModule(),
        NotesModule(),
        SettingsModule(),
        KnowledgeModule(),
        MiningModule(),
        ExchangeRateModule()
    ).flatten()
}