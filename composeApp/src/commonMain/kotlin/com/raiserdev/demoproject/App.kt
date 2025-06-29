package com.raiserdev.demoproject


import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.raiserdev.demoproject.data.CrossConfigDevice
import com.raiserdev.demoproject.data.SessionCache
import com.raiserdev.demoproject.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    crossConfigDevice: CrossConfigDevice ?= null,
    isTopBarVisible: Boolean) {


    MaterialTheme {
        SessionCache.configDevice = crossConfigDevice
        val navController = rememberNavController()
        AppNavigation(navController)
    }
}