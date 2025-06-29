package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.raiserdev.demoproject.utils.showToast
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.back
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsVM: SettingsViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            SettingsTopBar(
                title = "Settings",
                onBack = onBackClick
            )
        }
    ) { paddingValues ->
        BodySettings(
            modifier = Modifier.padding(paddingValues)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(
    title: String,
    onBack: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = stringResource(Res.string.back),
                )
            }
        },
        actions = {
            ActionsProfile {
                showToast("Editar perfil")
            }
        }
    )
}

@Composable
fun ActionsProfile(
    onEditProfileClick: () -> Unit,
) {
    Column {
        Row {
            IconButton(
                onClick = onEditProfileClick
            ) {
                Icon(
                    imageVector = Icons.Filled.SmartToy,
                    contentDescription = "Icono de usuario"
                )
            }

        }
    }

}

@Composable
fun BodySettings(modifier: Modifier = Modifier) {
    val settings = remember { mutableStateOf(false) }
    val language = remember { mutableStateOf(false) }

    Column(modifier.fillMaxWidth().padding(15.dp)) {
        TitleModuleSettings("APP SETTINGS", Icons.Filled.Apps)
        Spacer(modifier = Modifier.height(10.dp))
        SwitchOptionSettings("Modo Oscuro", settings.value) {
            showToast("Modo Oscuro $it")
            settings.value = it
        }
        SwitchOptionSettings("App Language", language.value) {
            showToast("Languae $it")
            language.value = it
        }

        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = {

            }

        ) {
            Text("Logout")
        }
    }
}

@Composable
fun TitleModuleSettings(title: String, imageVector: ImageVector){
    val modifier: Modifier = Modifier.fillMaxWidth()
    Row(
        modifier = modifier,
    ) {
      Icon(
          imageVector = imageVector,
          contentDescription = "Title module for settings"
      )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun SwitchOptionSettings(title:String, value:Boolean, onValueChange:(Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        Switch(checked = value, onCheckedChange = onValueChange)
    }
}



/*
@Composable
fun SettingsDialog(
    onCloseDialogClick: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
) {
    Dialog(onDismissRequest = { onCloseDialogClick(false) }) {
        Surface(
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Settings", style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(10.dp))
                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {onCloseDialogClick.invoke(false)}) { Text("Cerrar") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = onSaveClick) { Text("Guardar") }
                }
            }
        }
    }
}*/