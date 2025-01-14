package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit
) {
    val homeViewModel = koinViewModel<HomeViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            Text("Home Screen")
            Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones

            Button(
                onClick = onSettingsClick
            ) {
                Text("Settings")
            }
        }
    }

}