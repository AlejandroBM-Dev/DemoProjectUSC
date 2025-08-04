package com.raiserdev.demoproject.ui.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.raiserdev.demoproject.data.db.model.Nota
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.accept
import demoprojectusc.composeapp.generated.resources.cancel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteNoteDialog(
    modifier: Modifier = Modifier,
    idNota: Int,
    onDismiss: () -> Unit,
    onDelete: (id: Int) -> Unit
) {
    BasicAlertDialog(
        modifier = modifier.background(color = Color.White, shape = RoundedCornerShape(15.dp)),
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = true
        ),
        content = {
            Column(
                modifier = modifier
                    .wrapContentSize()
                    .padding(15.dp)
            ) {
                Text(
                    text = "Eliminar nota.",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "¿Quieres eliminar esta nota?",
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        onClick = {
                            onDismiss.invoke()
                        }
                    ) {
                        Text(
                            stringResource(Res.string.cancel)
                        )
                    }
                    Spacer(modifier = Modifier.width(18.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                        onClick = {
                            onDelete.invoke(idNota)
                        }
                    ) {
                        Text(
                            stringResource(Res.string.accept)
                        )
                    }
                }

            }

        }
    )
}