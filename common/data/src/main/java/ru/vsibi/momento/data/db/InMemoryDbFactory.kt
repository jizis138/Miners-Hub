package ru.vsibi.momento.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.reflect.KClass


class InMemoryDbFactory(private val context: Context) : DatabaseFactory {

    override fun <T : RoomDatabase> create(dbClass: KClass<T>, name: String): Lazy<T> = lazy {
        Room
            .inMemoryDatabaseBuilder(context, dbClass.java)
            .allowMainThreadQueries()
            .build()
    }
}