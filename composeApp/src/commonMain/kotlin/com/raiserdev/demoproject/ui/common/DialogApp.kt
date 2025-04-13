package com.raiserdev.demoproject.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun NotesDialogApp(
    title: String = "Custom title",
    message: String = "Custom message",
    showPositiveButton: Boolean = true,
    showNegativeButton: Boolean = true,
    onDismissRequest: () -> Unit,
    onPositiveClick:() -> Unit,
    onNegativeClick:() -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest.invoke() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.h5

                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = message,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    if (showNegativeButton) {
                        TextButton(
                            onClick = { onNegativeClick.invoke() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Cancel")
                        }
                    }

                    if (showPositiveButton) {
                        TextButton(
                            onClick = { onPositiveClick.invoke() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Accept")
                        }
                    }

                }
            }
        }
    }
}