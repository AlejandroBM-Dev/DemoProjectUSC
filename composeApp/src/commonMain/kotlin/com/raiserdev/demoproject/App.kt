package com.raiserdev.demoproject


import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import app.cash.sqldelight.db.SqlDriver
import com.raiserdev.demoproject.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(sqlDriver: SqlDriver, isTopBarVisible: Boolean) {
    MaterialTheme {
        val navController = rememberNavController()
        AppNavigation(navController)
    }
}