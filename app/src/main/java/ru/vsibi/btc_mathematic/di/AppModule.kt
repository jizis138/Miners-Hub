package ru.vsibi.btc_mathematic.di

import org.koin.core.module.Module
import ru.vsibi.btc_mathematic.data.di.DataModule
import ru.vsibi.btc_mathematic.exchange_rate_impl.di.ExchangeRateModule
import ru.vsibi.btc_mathematic.knowledge_impl.di.KnowledgeModule
import ru.vsibi.btc_mathematic.main_impl.di.MainModule
import ru.vsibi.btc_mathematic.mining_impl.di.MiningModule
import ru.vsibi.btc_mathematic.network.di.NetworkModule
import ru.vsibi.btc_mathematic.settings_impl.di.SettingsModule


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
        SettingsModule(),
        KnowledgeModule(),
        MiningModule(),
        ExchangeRateModule()
    ).flatten()
}