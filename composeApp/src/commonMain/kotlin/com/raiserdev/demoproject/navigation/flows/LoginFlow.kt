package com.raiserdev.demoproject.navigation.flows

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.raiserdev.demoproject.navigation.data.Screen
import com.raiserdev.demoproject.ui.presentation.login.HelpScreen
import com.raiserdev.demoproject.ui.presentation.login.LoginScreen
import com.raiserdev.demoproject.ui.presentation.login.LoginViewModel
import com.raiserdev.demoproject.ui.presentation.login.RegisterScreen
import org.koin.compose.viewmodel.koinViewModel


fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit, // Callback para pasar al siguiente flujo
    onRegisterSuccess: () -> Unit,
) {
    composable(Screen.Login.route) {
        val loginVM = koinViewModel<LoginViewModel>()

        LoginScreen(
            loginVM = loginVM,
            onLoginSuccess = onLoginSuccess,
            onRegisterClick = {
                navController.navigate(Screen.Register.route)
            },
            onHelpClick = {
                navController.navigate(Screen.Help.route)
            }
        )
    }

    composable(Screen.Register.route) {
        RegisterScreen(
            onRegisterSuccess = onRegisterSuccess,
            onHelpClick = {
                navController.navigate(Screen.Help.route)
            }
        )
    }

    composable(Screen.Help.route) {
        HelpScreen(
            onBack = {
                navController.popBackStack()
            }
        )
    }
}