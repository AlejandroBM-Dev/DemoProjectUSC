package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotaScreen(
    notaVM: NotaViewModel,
    noteId: Int = 0,
    modifier: Modifier = Modifier,
    onAddNoteClick: () -> Unit,
    onEditNoteClick: (noteId: Int) -> Unit,
    onCloseNoteClick: () -> Unit,
) {

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Head(
            title = notaVM.notes.value.titulo ?: "Nueva nota.",
            date = notaVM.notes.value.fecha ?: "Created ##-##-####"
        )
        Body()
        Foot(
            onCanceleClick = onCloseNoteClick,
            onAddNoteClick = onAddNoteClick
        )
    }

}

@Composable
fun Head(
    title: String = "Nueva nota.",
    date: String = "Created ##-##-####"
) {
    val modifier = Modifier.fillMaxWidth()
    Text(
        text = "NotaScreen",
        color = Color.Gray,
        textAlign = TextAlign.Center,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
    )
    Text("Created 2023-05-01",
        color = Color.Gray,
        textAlign = TextAlign.Center,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun Body() {
    val modifier = Modifier
    var textContent by rememberSaveable { mutableStateOf("") }
    Row (
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            value = textContent,
            onValueChange = {
                textContent = it
            },
            modifier = modifier.fillMaxWidth().fillMaxSize(0.9F)
        )
    }
}

@Composable
fun Foot(
    onCanceleClick: () -> Unit = {},
    onAddNoteClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Button(
            onClick = onCanceleClick,
            modifier = Modifier.size(55.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close note")
        }

        VerticalDivider(
            modifier = Modifier.padding(horizontal = 8.dp),
        )

        Button(
            onClick = onAddNoteClick,
            modifier = Modifier.size(55.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add note")
        }
    }
}