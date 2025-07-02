package com.raiserdev.demoproject.navigation.data

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String, val navArgument: List<NamedNavArgument> = emptyList()) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Help : Screen("help")
    data object Home : Screen("home")
    data object Notas : Screen(
        route = "notas/{${ArgParams.NOTE_ID}}",
        navArgument = listOf(navArgument(ArgParams.NOTE_ID) {
            type = NavType.Companion.LongType
        })
    ) {
        fun createRoute(noteId: Long) = route.replace(ArgParams.toPath(ArgParams.NOTE_ID), noteId.toString())
    }
    data object Settings : Screen("settings")
}

object ArgParams {
    const val NOTE_ID = "noteId"

    fun toPath(param: String) = "{${param}}"
}