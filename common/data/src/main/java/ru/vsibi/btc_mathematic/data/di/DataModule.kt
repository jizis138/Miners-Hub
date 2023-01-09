package ru.vsibi.btc_mathematic.data.di

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.vsibi.btc_mathematic.data.db.DatabaseFactory
import ru.vsibi.btc_mathematic.data.db.InMemoryDbFactory
import ru.vsibi.btc_mathematic.data.db.SimpleDbFactory
import ru.vsibi.btc_mathematic.data.di.qualifier.StorageQualifier
import ru.vsibi.btc_mathematic.di.bindSafe
import ru.vsibi.btc_mathematic.di.single

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