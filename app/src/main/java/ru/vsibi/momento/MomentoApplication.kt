/**
 * Created by Dmitry Popov on 01.10.2022.
 */
package ru.vsibi.momento

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.vsibi.momento.di.AppModule

class MomentoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            allowOverride(false)
            androidContext(this@MomentoApplication)
            modules(AppModule())
        }
    }

}