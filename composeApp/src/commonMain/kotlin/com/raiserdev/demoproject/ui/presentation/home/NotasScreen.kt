package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raiserdev.demoproject.ui.common.bar.NotesTopAppBar
import com.raiserdev.demoproject.utils.NEW_NOTE_ID
import com.raiserdev.demoproject.utils.showToast

@Composable
fun NotasScreen(
    notasVM: NotasViewModel,
    noteId: Long,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    if (noteId != NEW_NOTE_ID) {
        notasVM.getNoteData(noteId)
    }

    val titleNote = notasVM.titleNote.collectAsState()
    val modifier = Modifier

    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            NotesTopAppBar(
                title = if (titleNote.value.isNullOrEmpty()) {
                    "Nueva nota"
                } else {
                    titleNote.value
                },
                onBack = {
                    onBackClick.invoke()
                },
                onEditUser = {}
            )
        },
        bottomBar = { NotasBottomAppBar(
            notasVM = notasVM,
            onBackClick
        ) },
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            NotasHead(
                notaVM = notasVM
            )
            NotasBody(
                notaVM = notasVM
            )
        }

    }
}

@Composable
fun NotasHead(
    notaVM: NotasViewModel,
) {
    val modifier = Modifier.fillMaxWidth().padding(10.dp)
    val titleNote = notaVM.titleNote.collectAsState()
    OutlinedTextField(
        value = titleNote.value,
        onValueChange = {
            println("notaTitle: $it")
            notaVM.onTitleNoteChange(it)
                        },
        label = { Text("Agrega un titulo.") },
        modifier = modifier.padding(10.dp).fillMaxWidth()
    )
}

@Composable
fun NotasBody(
    notaVM: NotasViewModel,
) {
    val modifier = Modifier.fillMaxWidth().padding(10.dp)
    val scroll = rememberScrollState(0)
    val contentNote = notaVM.contentNote.collectAsState()
    TextField(
        value = contentNote.value,
        onValueChange = { notaVM.onContentNoteChange(it) },
        label = { Text("Título de la nota") },
        modifier = modifier.scrollable(state = scroll, orientation = Orientation.Vertical).fillMaxHeight()
    )
}

@Composable
fun NotasBottomAppBar(
    notasVM: NotasViewModel,
    onBack: () -> Unit
) {
    BottomAppBar(
        actions = {
            /*IconButton( onClick = {
                showToast("Bolt")
            } ) {
                Icon(Icons.Filled.Bolt, contentDescription = "Bolt")
            }*/
            /*IconButton( onClick = {
                showToast("Camera")
            } ) {
                Icon(Icons.Filled.Camera, contentDescription = "Camera")
            }*/
            IconButton( onClick = {
                showToast("Search")
            } ) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showToast("test saved.")
                    notasVM.saveNote { success ->
                        if (success) {
                            onBack.invoke()
                        }
                    } },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Save, "Save note.")
            }
        }
    )

}

/*@Preview
@Composable
fun NotasScreenPreview() {
    val previewVM = NotasViewModel(fakeNotasRepo).apply {
        onTitleNoteChange("Preview title")
        onContentNoteChange("Preview content")
    }
    NotasScreen(
        notasVM = previewVM,
        onBackClick = {},
        onSettingsClick = {}
    )
}*/