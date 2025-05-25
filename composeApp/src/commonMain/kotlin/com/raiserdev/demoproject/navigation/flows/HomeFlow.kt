package com.raiserdev.demoproject.navigation.flows

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raiserdev.demoproject.navigation.data.Screen
import com.raiserdev.demoproject.ui.presentation.home.HomeScreen
import com.raiserdev.demoproject.ui.presentation.home.NotaScreen
import com.raiserdev.demoproject.ui.presentation.home.SettingsScreen
import com.raiserdev.demoproject.utils.showToast

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeNavGraph(
    navController:NavController,
) {
    composable(Screen.Home.route) {
        HomeScreen(
            onClickNote = {
                navController.navigate(Screen.NoteDetail(it).route)
            },
            onSettingsClick = {
                showToast("Settings clicked")
            },
            onCloseSession = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true } // Esto limpia todo el backstack
                    launchSingleTop = true // Evita múltiples instancias de Login
                    restoreState = false
                }
            }
        )
    }
    composable(
        route = Screen.NoteDetail(0).route,
    ) {
        val noteId = it.arguments?.getInt("noteId") ?: 0
        NotaScreen(
            noteId = noteId,
            modifier = Modifier,
            onAddNoteClick = {

            },
            onEditNoteClick = { noteId ->

            },
            onCloseNoteClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(0) { inclusive = true } // Esto limpia todo el backstack
                    launchSingleTop = true // Evita múltiples instancias de Login
                    restoreState = false
                }
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