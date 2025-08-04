package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raiserdev.demoproject.data.db.model.LabelsData
import com.raiserdev.demoproject.data.db.model.Nota
import com.raiserdev.demoproject.ui.common.LabelItem
import com.raiserdev.demoproject.ui.common.bar.NotesBottomAppBar
import com.raiserdev.demoproject.ui.common.bar.NotesTopAppBar
import com.raiserdev.demoproject.ui.common.dialog.CloseSessionDialog
import com.raiserdev.demoproject.ui.common.dialog.DeleteNoteDialog
import com.raiserdev.demoproject.utils.NEW_NOTE_ID
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    homeVM: HomeViewModel = koinViewModel(),
    onSettingsClick: () -> Unit,
    onBack: () -> Unit,
    onAddNote: (Long) -> Unit,
    onShowNote: (idNote: Long) -> Unit,
    onEditUser: () -> Unit,
) {

    homeVM.apply {
        showAllNotes()
        getStatsChangeGridOrList()
        getLabels()
    }

    val showAllLabels = homeVM.labelsList.collectAsState()
    val showAllNotes = homeVM.listNotes.collectAsState()

    val showDeleteDialog = homeVM.showDeleteDialog.collectAsState()
    val showCloseDialog = homeVM.showCloseSessionDialog.collectAsState()

    val updateGridOrList = homeVM.updateGridOrList.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(5.dp),
        topBar = {
            NotesTopAppBar(
                title = "HomeScreen",
                onBack = {
                    homeVM.showCloseDialog(true)
                },
                onEditUser = {
                    onEditUser.invoke()
                },
            )
        },
        bottomBar = {
            NotesBottomAppBar(
                homeVM = homeVM,
                onSettingsClick = { onSettingsClick.invoke() },
                onAddNote = { onAddNote(NEW_NOTE_ID) },
                onUpdateViewGridOrList = {
                    homeVM.changeGridOrList(!homeVM.updateGridOrList.value)
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                "NOTAS",
                style = MaterialTheme.typography.headlineMedium
            )

            LabelsComponent(
                labelsList = showAllLabels.value,
                showAllNotes = {
                    homeVM.showAllNotes()
                }
            ) {
                homeVM.getFilterByLabelId(it.toLong())
            }

            if (showAllNotes.value.isNotEmpty()) {
                NotesListComponent(
                    homeVM = homeVM,
                    onShowNote = onShowNote,
                    onDeleteNote = { idNota ->
                        homeVM.showDeleteDialog(true,idNota)
                    },
                    showAllNotes = showAllNotes.value,
                    updateGridOrList = updateGridOrList.value
                )
            } else {
                EmptyList()
            }

        }

    }

    showDeleteDialog.let {
        if (it.value.first) {
            DeleteNoteDialog(
                idNota = it.value.second,
                onDismiss = {
                    homeVM.showDeleteDialog(false)
                },
                onDelete = { idNota ->
                    homeVM.onDeleteNote(idNote = idNota)
                    homeVM.triggerAnimatedDelete(it.value.second)
                    homeVM.showDeleteDialog(false)
                }
            )
        }
    }

    showCloseDialog.let {
        if (it.value) {
            CloseSessionDialog(
                onDismiss = {
                    homeVM.showCloseDialog(false)
                },
                onConfirm = {
                    homeVM.clearUserPreferences {
                        onBack.invoke()
                    }
                    homeVM.showCloseDialog(false)
                }
            )
        }
    }

}

@Composable
fun EmptyList() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.LightGray),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                imageVector = Icons.AutoMirrored.Filled.Notes,
                modifier = Modifier.size(100.dp),
                contentDescription = "No hay notas"
            )

            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "No hay notas",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Composable
fun NotesListComponent(
    homeVM: HomeViewModel,
    onShowNote: (idNote: Long) -> Unit,
    onDeleteNote: (idNote: Int) -> Unit,
    showAllNotes: List<Nota>,
    updateGridOrList: Boolean
) {
    val pendingDeleteId = homeVM.pendingNoteToDeleteId.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize =
            if (updateGridOrList) {
                250.dp
            } else {
                125.dp
            }
        )
    ) {

        items(
            items = showAllNotes,
            key = {it.id}
        ) { note ->
            var visible by remember(note.id) { mutableStateOf(true) }
            //var pendingDelete by remember(note.id) { mutableStateOf(false) }
            println("pendingDeleteId: ${pendingDeleteId.value} ${note.id} $visible")
            if (pendingDeleteId.value == note.id.toInt() && visible) {
                visible = false
                LaunchedEffect(note.id) {
                    delay(300) // Match this to your animation duration
                    onDeleteNote.invoke(note.id.toInt())
                    homeVM.clearPendingDelete()
                }
            }
            AnimatedVisibility(
                visible = visible,
                exit = fadeOut() + shrinkVertically(),
            ) {
                ItemCard(
                    cardData = CardData(
                        idNote = note.id.toInt(),
                        title = note.titulo,
                        textCard = note.contenido?.take(20).toString(),
                        dateCreated = note.fechaCreacion,
                        backgroundColor = Color.Red,
                    ),
                    onDeleteClick = { noteId ->
                        println("onDeleteClick: $noteId")
                        homeVM.showDeleteDialog(true,noteId)
                    },
                    onEditClick = {
                        onShowNote.invoke(it.toLong())
                    }
                )
            }

        }
    }
}
@Composable
fun LabelsComponent(
    labelsList: List<LabelsData>,
    showAllNotes: () -> Unit,
    onFilterLabel: (idLabel: Int) -> Unit
) {
    Row {
        TextButton(
            modifier = Modifier.padding(5.dp),
            onClick = {
                showAllNotes.invoke()
            }
        ) {
            Text("Show All")
        }
        Spacer(modifier = Modifier.width(5.dp))
        LazyRow(
            modifier = Modifier.padding(start = 5.dp, end = 5.dp).fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(labelsList) { label ->

                LabelItem(
                    data = label,
                ){
                    onFilterLabel.invoke(it)
                }
            }
        }

    }
}

@Composable
fun ItemCard(
    cardData: CardData,
    updateGridOrList: Boolean = false,
    onDeleteClick: (idNote:Int) -> Unit,
    onEditClick: (idNote:Int) -> Unit
) {
    val modifierContentCard = Modifier.padding(2.dp)
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .size(width = 300.dp, height = 120.dp)
            .padding(5.dp)
            .combinedClickable(
                onClick = {
                    onEditClick.invoke(cardData.idNote)
                },
                onLongClick = {
                    onDeleteClick.invoke(cardData.idNote)
                }
            ),

    ) {
        Column(
            modifier = Modifier.padding(5.dp).fillMaxSize()
        ) {
            Text(
                text = cardData.title,
                maxLines = 1,
                fontSize = 18.sp,
                modifier = modifierContentCard
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = cardData.textCard,
                fontSize = 12.sp,
                maxLines = if (updateGridOrList)1 else 2,
                modifier = modifierContentCard
            )
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = modifierContentCard.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd,
            ){
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar notas.",
                )
            }
        }

    }
}

data class CardData(
    val idNote: Int,
    val title: String,
    val textCard: String,
    val dateCreated: String,
    val backgroundColor: Color
)

