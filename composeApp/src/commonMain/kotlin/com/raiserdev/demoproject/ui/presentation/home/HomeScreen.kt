package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.error_exception
import org.jetbrains.compose.resources.stringResource
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
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Home Screen",
                style = MaterialTheme.typography.h3
            )
            nothingHere()
        }
    }

}

@Composable
fun nothingHere() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(Res.string.error_exception),
            fontSize = 70.sp,
        )
        Text(
            "Lo sentimos no hay nada por aquí",
            style = MaterialTheme.typography.h4
        )
    }
}