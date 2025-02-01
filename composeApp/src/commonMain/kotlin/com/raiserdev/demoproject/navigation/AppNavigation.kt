package com.raiserdev.demoproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.raiserdev.demoproject.navigation.data.Screen
import com.raiserdev.demoproject.navigation.flows.homeNavGraph
import com.raiserdev.demoproject.navigation.flows.loginNavGraph

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Agrega el grafo de Login; al tener una acción exitosa, navegamos al flujo Home
        loginNavGraph(
            navController = navController,
            onLoginSuccess = {
                // Limpia el back stack si es necesario y navega a Home
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            onRegisterSuccess = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Login.route) {inclusive = true}
                }
            }
        )
        // Agrega el grafo de Home
        homeNavGraph(
            navController = navController,
        )
    }
}