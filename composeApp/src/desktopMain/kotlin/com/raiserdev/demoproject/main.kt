package com.raiserdev.demoproject

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.raiserdev.demoproject.data.DriverFactory

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DemoProjectUSC",
    ) {
        App(DriverFactory().createDriver(), true)
    }
}
