package ru.vsibi.momento.data.di

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vsibi.momento.data.db.DatabaseFactory
import ru.vsibi.momento.data.db.InMemoryDbFactory
import ru.vsibi.momento.data.db.SimpleDbFactory
import ru.vsibi.momento.data.di.qualifier.StorageQualifier
import ru.vsibi.momento.di.bindSafe
import ru.vsibi.momento.di.single

object DataModule {
    operator fun invoke(): List<Module> =
        listOf(
            createDatabase(),
        )

    private fun createDatabase() = module {
        single(
            ::InMemoryDbFactory,
            named(StorageQualifier.InMemory)
        ) bindSafe DatabaseFactory::class

        single(
            ::SimpleDbFactory,
            named(StorageQualifier.Simple)
        ) bindSafe DatabaseFactory::class

    }
}