package com.raiserdev.demoproject.navigation.flows

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raiserdev.demoproject.navigation.data.Screen
import com.raiserdev.demoproject.ui.presentation.home.HomeScreen
import com.raiserdev.demoproject.ui.presentation.home.HomeViewModel
import com.raiserdev.demoproject.ui.presentation.home.NotasScreen
import com.raiserdev.demoproject.ui.presentation.home.NotasViewModel
import com.raiserdev.demoproject.ui.presentation.home.SettingsScreen
import com.raiserdev.demoproject.ui.presentation.home.SettingsViewModel
import com.raiserdev.demoproject.utils.showToast
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeNavGraph(
    navController:NavController,
) {
    composable(Screen.Home.route) {
        val homeViewModel = koinViewModel<HomeViewModel>()

        HomeScreen(
            homeVM = homeViewModel,
            onSettingsClick = {
                navController.navigate(Screen.Settings.route)
            },
            onBack = {
                navController.popBackStack()
            },
            onAddNote = {
                navController.navigate(Screen.Notas.route)
            }
        )
    }
    composable(Screen.Settings.route) {
        val settingsVM = koinViewModel<SettingsViewModel>()

        SettingsScreen(
            settingsVM = settingsVM,
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
    composable(Screen.Notas.route) {
        val notaVM = koinViewModel<NotasViewModel>()

        NotasScreen(
            notasVM = notaVM,
            onBackClick = {
                navController.popBackStack()
            },
            onSettingsClick = {
                navController.navigate(Screen.Settings.route)
            }
        )
    }
}