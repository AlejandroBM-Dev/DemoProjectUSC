package com.raiserdev.demoproject.ui.common


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CloseSessionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val modifier: Modifier = Modifier

    BasicAlertDialog(
        onDismissRequest
        = {
            onDismiss()
        },
        modifier = modifier,
        //properties = DialogProperties(),
        content = { })
}