package com.appknot.cardgame

import android.app.Application
import com.appknot.cardgame.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

/**
 *
 * @author Catherine on 2021-07-14
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidFileProperties()

            modules(listOf(
                appContext,
                viewModelModule
            ))
        }
    }
}