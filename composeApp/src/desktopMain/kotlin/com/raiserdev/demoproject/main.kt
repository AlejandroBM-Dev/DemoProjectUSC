package com.raiserdev.demoproject

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.raiserdev.demoproject.di.initKoin

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "DemoProjectUSC",
    ) {
        //App(DriverFactory().createDriver(), true)
    }
}
