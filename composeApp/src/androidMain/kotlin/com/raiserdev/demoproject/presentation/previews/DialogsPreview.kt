package com.raiserdev.demoproject.presentation.previews

import androidx.compose.runtime.Composable
import com.raiserdev.demoproject.ui.common.NotesDialogApp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Dialogs() {
    NotesDialogApp(
        title = "Dialog title",
        message = "This is me message in dialog custom.",
        showNegativeButton = true,
        showPositiveButton = true,
        onDismissRequest = {},
        onNegativeClick = {},
        onPositiveClick = {}
    )
}

