package com.raiserdev.demoproject.ui.common.bar

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.back
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopAppBar(
    title: String,
    onBack: () -> Unit,
    onEditUser: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = stringResource(Res.string.back)
                )
            }
        },
        actions = {
            IconButton(onClick = onEditUser) {
                Icon(
                    imageVector = Icons.Filled.SmartToy,
                    contentDescription = stringResource(Res.string.back)
                )
            }
        }
    )
}