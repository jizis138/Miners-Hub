package ru.vsibi.momento.di

import org.koin.core.module.Module
import ru.vsibi.momento.data.di.DataModule
import ru.vsibi.momento.main_impl.di.MainModule
import ru.vsibi.momento.notes_impl.di.NotesModule
import ru.vsibi.momento.settings_impl.di.SettingsModule


object AppModule {

    operator fun invoke() = listOf(
        createFeatureModules(),
        PresentationModule(),
        listOf(EnvironmentModule()),
//        NetworkModule(),
        DataModule(),
//        listOf(ComponentModule())
    ).flatten()

    private fun createFeatureModules(): List<Module> = listOf(
        MainModule(),
        NotesModule(),
        SettingsModule(),
    ).flatten()
}