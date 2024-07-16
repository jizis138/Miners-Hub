package ru.vsibi.btc_mathematic.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlin.reflect.KClass

class SimpleDbFactory(private val context: Context) : DatabaseFactory {

    override fun <T : RoomDatabase> create(dbClass: KClass<T>, name: String): Lazy<T> = lazy {
        Room.databaseBuilder(context, dbClass.java, name).addMigrations(
            HistoryMigration5To7(),
            FarmMigration2To3(),
        ).fallbackToDestructiveMigration().build()
    }
}

class HistoryMigration5To7 : Migration(5, 7) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Создание новой таблицы HistoryTable с дополнительным столбцом usingViaBtc
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `HistoryTable_new` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`date` TEXT NOT NULL, " +
                    "`hashrate` REAL NOT NULL, " +
                    "`power` REAL NOT NULL, " +
                    "`electricityPrice` TEXT NOT NULL, " +
                    "`miners` TEXT NOT NULL, " +
                    "`perDay` REAL NOT NULL, " +
                    "`perMonth` REAL NOT NULL, " +
                    "`exchangeRate` TEXT NOT NULL, " +
                    "`difficulty` TEXT NOT NULL, " +
                    "`blockIncome` REAL NOT NULL, " +
                    "`powerPerMonth` REAL NOT NULL, " +
                    "`btcIncomePerDay` REAL NOT NULL, " +
                    "`btcIncomePerMonth` REAL NOT NULL, " +
                    "`incomePerMonth` INTEGER NOT NULL, " +
                    "`pricePowerPerMonth` INTEGER NOT NULL, " +
                    "`usingViaBtc` INTEGER NOT NULL)"
        )

        // Копирование данных из старой таблицы в новую
        database.execSQL(
            "INSERT INTO `HistoryTable_new` " +
                    "(`id`, `date`, `hashrate`, `power`, `electricityPrice`, `miners`, `perDay`, " +
                    "`perMonth`, `exchangeRate`, `difficulty`, `blockIncome`, `powerPerMonth`, " +
                    "`btcIncomePerDay`, `btcIncomePerMonth`, `incomePerMonth`, `pricePowerPerMonth`, `usingViaBtc`) " +
                    "SELECT `id`, `date`, `hashrate`, `power`, `electricityPrice`, `miners`, `perDay`, " +
                    "`perMonth`, `exchangeRate`, `difficulty`, `blockIncome`, `powerPerMonth`, " +
                    "`btcIncomePerMonth` / 30.0 AS `btcIncomePerDay`, `btcIncomePerMonth`, `incomePerMonth`, `pricePowerPerMonth`, 0 " +
                    "FROM `HistoryTable`"
        )

        // Удаление старой таблицы
        database.execSQL("DROP TABLE `HistoryTable`")

        // Переименование новой таблицы в оригинальное имя
        database.execSQL("ALTER TABLE `HistoryTable_new` RENAME TO `HistoryTable`")
    }
}

class FarmMigration2To3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Создание новой таблицы FarmTable с дополнительным столбцом usingViaBtc
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `FarmTable_new` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`title` TEXT NOT NULL, " +
                    "`usingViaBtc` INTEGER NOT NULL, " +
                    "`miners` TEXT NOT NULL, " +
                    "`electricityPrice` TEXT NOT NULL)"
        )

        // Копирование данных из старой таблицы в новую
        database.execSQL(
            "INSERT INTO `FarmTable_new` " +
                    "(`id`, `title`, `usingViaBtc`, `miners`, `electricityPrice`) " +
                    "SELECT `id`, `title`, 0 AS `usingViaBtc`, `miners`, `electricityPrice` " +
                    "FROM `FarmTable`"
        )

        // Удаление старой таблицы
        database.execSQL("DROP TABLE `FarmTable`")

        // Переименование новой таблицы в оригинальное имя
        database.execSQL("ALTER TABLE `FarmTable_new` RENAME TO `FarmTable`")
    }
}