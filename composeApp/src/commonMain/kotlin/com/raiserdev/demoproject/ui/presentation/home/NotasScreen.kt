package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NewLabel
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.raiserdev.demoproject.ui.common.bar.NotesTopAppBar
import com.raiserdev.demoproject.ui.common.dialog.LabelsDialog
import com.raiserdev.demoproject.utils.showToast

@Composable
fun NotasScreen(
    notasVM: NotasViewModel,
    noteId: Long,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    LaunchedEffect(noteId) {
        notasVM.loadData(noteId)
    }

    val titleNote = notasVM.titleNote.collectAsState()
    val modifier = Modifier

    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            NotesTopAppBar(
                title = titleNote.value.ifEmpty {
                    "Nueva nota"
                },
                onBack = {
                    onBackClick.invoke()
                },
                onEditUser = {}
            )
        },
        bottomBar = {
            NotasBottomAppBar(
                notasVM = notasVM,
                onBack = onBackClick
            ) },
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            NotasHead(
                notaVM = notasVM,
                onSelectLabel = { idLabel ->
                    println("Head notes.  idLabel: $idLabel")
                    notasVM.setIdLabelChange(idLabel)
                }
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
    onSelectLabel: (Int) -> Unit
) {
    val modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
    val titleNote = notaVM.titleNote.collectAsState()
    val showAllLabels = notaVM.labels.collectAsState()
    val labelSelectedColor = notaVM.colorLabel.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = titleNote.value,
            onValueChange = { notaVM.setTitleNoteChange(it) },
            label = { Text("Agrega un titulo.") },
            modifier = Modifier.fillMaxWidth().weight(0.8f)
        )
        Spacer(modifier = Modifier.width(15.dp))
        IconButton(
            onClick = {
                showDialog = !showDialog
                // onSelectLabel.invoke()
            },
            modifier = Modifier.clip(CircleShape).background(labelSelectedColor.value)
        ) {
            Icon(Icons.Filled.NewLabel, contentDescription = "Select label",tint = Color.White)
        }
    }
    if (showDialog) {
        LabelsDialog(
            showAllLabels.value,
            notaVM.idLabel.value,
            onDismiss = { showDialog = false },
            onConfirm = { labelId ->
                onSelectLabel.invoke(labelId)
                showDialog = false
            }
        )
    }
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
        onValueChange = { notaVM.setContentNoteChange(it) },
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