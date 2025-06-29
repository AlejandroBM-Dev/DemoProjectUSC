package com.raiserdev.demoproject.navigation.data

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Help : Screen("help")
    data object Home : Screen("home")
    data object Notas : Screen("notas")
    data object Settings : Screen("settings")
}