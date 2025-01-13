package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun LoginScreen (vm: LoginViewModel){
    val credentials by vm.credentials.collectAsState()
}