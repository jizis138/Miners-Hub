package ru.vsibi.momento.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.vsibi.momento.MainActivityViewModel
import ru.vsibi.momento.navigation.RootRouter
import ru.vsibi.momento.navigation.RootRouterImpl
import ru.vsibi.momento.navigation.CustomCicerone

object PresentationModule {

    operator fun invoke(): List<Module> = listOf(
        createNavigationModule(),
        createMainActivityModule()
    )

    private fun createNavigationModule() = module {
        single { Cicerone.create(RootRouterImpl()) }
        single { get<CustomCicerone>().getNavigatorHolder() }
        single { get<CustomCicerone>().router }
            .bindSafe(RootRouter::class)
            .bindSafe(Router::class)
    }

    private fun createMainActivityModule() = module {
        viewModel(::MainActivityViewModel)
        factory(MainActivityViewModel::Features)
    }
}