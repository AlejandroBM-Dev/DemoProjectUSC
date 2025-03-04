package com.raiserdev.demoproject

import androidx.compose.ui.window.ComposeUIViewController
import com.raiserdev.demoproject.data.DriverFactory
import com.raiserdev.demoproject.di.appModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    App(
        CrossConfigDevice(),
        true
    )
}

fun initKoin() {
    startKoin {
        modules(
            appModule(
                ProjectDatabase.invoke(
                    DriverFactory().createDriver()
                )
            )
        )
    }.koin
}