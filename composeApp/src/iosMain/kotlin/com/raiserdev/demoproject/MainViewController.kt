package com.raiserdev.demoproject

import androidx.compose.ui.window.ComposeUIViewController
import com.raiserdev.demoproject.data.DriverFactory

fun MainViewController() = ComposeUIViewController {
    App(
        CrossConfigDevice(),
        DriverFactory().createDriver(),
        true
    )
}