package com.raiserdev.demoproject

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.raiserdev.demoproject.data.DriverFactory
import com.raiserdev.demoproject.di.initKoin
import org.koin.core.context.startKoin

fun main() = application {
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "DemoProjectUSC",
    ) {
        App(DriverFactory().createDriver(), true)
    }
}
