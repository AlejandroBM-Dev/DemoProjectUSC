package com.raiserdev.demoproject.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.back
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopAppBar(
    title: String,
    onBack:() -> Unit,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBack.invoke()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = stringResource(Res.string.back)
                )
            }
        }
    )
}