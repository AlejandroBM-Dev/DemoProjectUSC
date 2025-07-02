package com.raiserdev.demoproject.navigation.flows

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import com.raiserdev.demoproject.navigation.data.ArgParams
import com.raiserdev.demoproject.navigation.data.Screen
import com.raiserdev.demoproject.ui.presentation.home.HomeScreen
import com.raiserdev.demoproject.ui.presentation.home.HomeViewModel
import com.raiserdev.demoproject.ui.presentation.home.NotasScreen
import com.raiserdev.demoproject.ui.presentation.home.NotasViewModel
import com.raiserdev.demoproject.ui.presentation.home.SettingsScreen
import com.raiserdev.demoproject.ui.presentation.home.SettingsViewModel
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
                val route = Screen.Notas.createRoute(it)
                navController.navigate(
                    route = route,
                )
            },
            onShowNote = { idNote ->
                val route = Screen.Notas.createRoute(idNote)
                navController.navigate(
                    route = route,
                )
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
    composable(Screen.Notas.route) { navBackStackEntry ->
        val notaVM = koinViewModel<NotasViewModel>()
        val noteId = navBackStackEntry.arguments?.getString(ArgParams.NOTE_ID)
        println("noteId: $noteId")
        NotasScreen(
            notasVM = notaVM,
            noteId = noteId?.toLong() ?: -1L,
            onBackClick = {
                navController.popBackStack()
            },
            onSettingsClick = {
                navController.navigate(Screen.Settings.route)
            }
        )
    }
}