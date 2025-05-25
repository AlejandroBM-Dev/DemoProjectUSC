package com.raiserdev.demoproject.presentation.previews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raiserdev.demoproject.data.ItemNoteData
import com.raiserdev.demoproject.ui.common.ItemNote
import com.raiserdev.demoproject.ui.common.NewItemNotee
import com.raiserdev.demoproject.ui.presentation.home.NotaScreen
import com.raiserdev.demoproject.utils.showToast

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewNoteItem() {
    val modifier = Modifier.padding(30.dp)
    val mockData = ItemNoteData(
        noteId = 1,
        title = "Reunión de equipo",
        message = "Revisar el backlog y asignar tareas.",
        createdDate = "2025-05-01"
    )

    Column {
        ItemNote(
            modifier = modifier,
            data = mockData
        ) {
            // NOTHING HERE... :p
        }

        NewItemNotee(
            modifier = modifier
        ) {
            showToast("ADD NEW NOTE")
        }

    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewNoteScreen() {
    Scaffold { paddingValues ->
        NotaScreen(
            1,
            modifier = Modifier.padding(paddingValues),
            {},
            {},
            {}
        )
    }

}

