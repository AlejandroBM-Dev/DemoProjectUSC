package com.raiserdev.demoproject.ui.common.bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.raiserdev.demoproject.ui.presentation.home.HomeViewModel
import com.raiserdev.demoproject.utils.showToast
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotesBottomAppBar(
    homeVM: HomeViewModel = koinViewModel(),
    onSettingsClick: () -> Unit,
    onAddNote: () -> Unit,
    onUpdateViewGridOrList: () -> Unit
){
    val updateGridOrList = homeVM.updateGridOrList.collectAsState()
    BottomAppBar(
        actions = {

            IconButton(
                onClick = { showToast("Notas home.") },
            ) {
                Icon(
                    Icons.Filled.Home,
                    "Notas home"
                )
            }

            IconButton(
                onClick = { onUpdateViewGridOrList.invoke() },
            ) {
                Icon(
                    if (updateGridOrList.value) {
                        Icons.Filled.GridView
                    } else {
                        Icons.AutoMirrored.Filled.List
                    },
                    "Mostrar notas en grid."
                )
            }

            IconButton(
                onClick = { onSettingsClick.invoke() },
            ) {
                Icon(
                    Icons.Filled.Settings,
                    "Settings de nota."
                )
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddNote.invoke() },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Agregar nueva nota.")
            }
        }
    )
}