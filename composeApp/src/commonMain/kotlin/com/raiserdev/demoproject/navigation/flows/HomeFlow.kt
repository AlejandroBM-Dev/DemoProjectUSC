package com.raiserdev.demoproject.navigation.flows

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raiserdev.demoproject.navigation.data.Screen
import com.raiserdev.demoproject.ui.presentation.home.HomeScreen
import com.raiserdev.demoproject.ui.presentation.home.SettingsScreen
import com.raiserdev.demoproject.utils.showToast

fun NavGraphBuilder.homeNavGraph(
    navController:NavController,
) {
    composable(Screen.Home.route) {
        HomeScreen(
            onSettingsClick = {
                showToast("Settings clicked")
            }
        )
    }
    composable(Screen.Settings.route) {
        SettingsScreen(
            onBack = { /* Volver a Home u otra acción */ }
        )
    }
}