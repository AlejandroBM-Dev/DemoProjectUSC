package com.raiserdev.demoproject

import android.app.Application
import com.raiserdev.demoproject.data.DriverFactory
import com.raiserdev.demoproject.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(
                appModule(
                    ProjectDatabase.invoke(
                        DriverFactory(this@MainApplication).createDriver()
                    )
                )
            )
        }
        AndroidContextHolder.appContext = this@MainApplication
    }
}