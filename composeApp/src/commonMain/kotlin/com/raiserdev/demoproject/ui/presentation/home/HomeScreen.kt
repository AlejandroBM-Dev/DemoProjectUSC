package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raiserdev.demoproject.ui.common.NotesBottomAppBar
import com.raiserdev.demoproject.ui.common.NotesTopAppBar
import com.raiserdev.demoproject.utils.showToast
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    homeVM: HomeViewModel = koinViewModel(),
    onSettingsClick: () -> Unit,
    onBack: () -> Unit,
    onAddNote: () -> Unit,
    onShowNote: (idNote: Int) -> Unit
) {
    homeVM.showAllNotes()

    val showAllNotes = homeVM.listNotes.collectAsState()
    println("showAllNotes: ${showAllNotes.value}")
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NotesTopAppBar(
                title = "HomeScreen",
                onBack = {
                    homeVM.clearUserPreferences {
                        onBack.invoke()
                    }
                },
                onSettingsClick = {
                },
            )
        },
        bottomBar = {
            NotesBottomAppBar(
                onSettingsClick = { onSettingsClick.invoke() },
                onAddNote = { onAddNote.invoke() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                "NOTAS",
                style = MaterialTheme.typography.h3
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {
                items(showAllNotes.value.size) { index ->

                    ItemCard(
                        cardData = CardData(
                            idNote = showAllNotes.value[index].id.toInt(),
                            title = showAllNotes.value[index].titulo,
                            textCard = showAllNotes.value[index].contenido?.take(20).toString(),
                            dateCreated = showAllNotes.value[index].fechaCreacion,
                            backgroundColor = Color.Red,
                        )
                    ) {
                        showToast("idNote: $it")
                    }
                }
            }
        }
    }

}

@Composable
fun ItemCard(cardData: CardData,onEditClick:(idNote:Int) -> Unit) {
    val modifierContentCard = Modifier.padding(2.dp)
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .size(width = 300.dp, height = 100.dp)
            .padding(10.dp)
            .clickable {
                onEditClick.invoke(cardData.idNote)
            },
    ) {
        Text(
            text = cardData.title,
            fontSize = 18.sp,
            modifier = modifierContentCard
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = cardData.textCard,
            fontSize = 12.sp,
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

data class CardData(
    val idNote: Int,
    val title: String,
    val textCard: String,
    val dateCreated: String,
    val backgroundColor: Color
)

