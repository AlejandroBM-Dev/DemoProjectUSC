package com.raiserdev.demoproject.navigation.flows

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raiserdev.demoproject.navigation.data.Screen
import com.raiserdev.demoproject.ui.presentation.home.HomeScreen
import com.raiserdev.demoproject.ui.presentation.home.SettingsScreen
import com.raiserdev.demoproject.utils.showToast

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeNavGraph(
    navController:NavController,
) {
    composable(Screen.Home.route) {
        HomeScreen(
            onSettingsClick = {
                showToast("Settings clicked")
            },
            onBack = {

            }
        )
    }
    composable(Screen.Settings.route) {
        val sheetState = rememberModalBottomSheetState()

        SettingsScreen(
            sheetState = sheetState,
        )
    }
}