package com.raiserdev.demoproject.ui.common

import androidx.compose.material.icons.Icons
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
import com.raiserdev.demoproject.utils.showToast

@Composable
fun NotesBottomAppBar(
    onNoteClick: () -> Unit,
){
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
                onClick = { showToast("Grid notas.") },
            ) {
                Icon(
                    Icons.Filled.GridView,
                    "Mostrar notas en grid."
                )
            }

            IconButton(
                onClick = { showToast("Settings.") },
            ) {
                Icon(
                    Icons.Filled.Settings,
                    "Settings de nota."
                )
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNoteClick.invoke()
                          },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Agregar nueva nota.")
            }
        }
    )
}