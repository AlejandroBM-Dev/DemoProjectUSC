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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raiserdev.demoproject.ui.common.NotesBottomAppBar
import com.raiserdev.demoproject.ui.common.NotesDialogApp
import com.raiserdev.demoproject.ui.common.NotesTopAppBar
import com.raiserdev.demoproject.utils.showToast
import org.koin.compose.viewmodel.koinViewModel

val cardList: MutableList<CardData> = mutableListOf()

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    onCloseSession: () -> Unit,
) {
    val homeViewModel = koinViewModel<HomeViewModel>()
    var showCloseDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NotesTopAppBar(
                title = "HomeScreen",
                onBack = {
                    showCloseDialog = !showCloseDialog
                },
                onSettingsClick = {},
            )
        },
        bottomBar = {
            NotesBottomAppBar()
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

            cardList
                .add(
                    index = 0,
                    element = CardData(0,"Primera Nota", "Aquí veremos un poco de texto...", "10/02/2010", Color.Red)
                )
            cardList
                .add(
                    index = 1,
                    element = CardData(1,"Segunda Nota", "Aquí veremos un poco de texto...", "10/02/2010", Color.Red)
                )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {
                items(cardList.size) { cardItem ->
                    ItemCard(cardList[cardItem]) { idNote ->
                        showToast("Edit -> ${cardList[idNote].title}")
                    }
                }
            }
        }
    }

    if (showCloseDialog) {

        NotesDialogApp(
            title = "Cerrar sesión",
            message = "¿Deseas cerrar tú sesión actual?",
            showPositiveButton = true,
            showNegativeButton = true,
            onDismissRequest = {
                showCloseDialog = !showCloseDialog
            },
            onNegativeClick = {
                showToast("No deseo sesión")
                showCloseDialog = !showCloseDialog
            },
            onPositiveClick = {
                showToast("Cerrar sesión")
                homeViewModel.closeApp()
                onCloseSession.invoke()
            },
        )

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

