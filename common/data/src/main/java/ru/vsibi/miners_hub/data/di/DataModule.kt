package ru.vsibi.miners_hub.data.di

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vsibi.miners_hub.data.db.DatabaseFactory
import ru.vsibi.miners_hub.data.db.InMemoryDbFactory
import ru.vsibi.miners_hub.data.db.SimpleDbFactory
import ru.vsibi.miners_hub.data.di.qualifier.StorageQualifier
import ru.vsibi.miners_hub.di.bindSafe
import ru.vsibi.miners_hub.di.single

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