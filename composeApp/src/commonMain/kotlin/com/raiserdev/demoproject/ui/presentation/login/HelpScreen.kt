package com.raiserdev.demoproject.ui.presentation.login

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
fun HelpScreen(onBack: () -> Unit) {
    val helpViewModel = koinViewModel<HelpViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            Text("Help Screen")
            Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones

            Button(
                onClick = onBack
            ) {
                Text("Back")
            }
        }
    }
}