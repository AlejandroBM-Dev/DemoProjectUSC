package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    val settingsViewModel = koinViewModel<SettingsViewModel>()
}