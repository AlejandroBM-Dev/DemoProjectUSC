package com.raiserdev.demoproject.ui.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.raiserdev.demoproject.data.db.model.LabelsData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelsDialog(
    labelList: List<LabelsData>,
    idSelectedLabel: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
   var selectedId by remember { mutableStateOf(idSelectedLabel) }

    BasicAlertDialog(
        modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(15.dp)),
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = true
        ),
    ) {
        Column(
            modifier = Modifier.wrapContentSize().padding(15.dp)
        ) {
            Text(
                text = "Selecciona una etiqueta",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            ) {
                items(labelList.size) {

                    LabelItem(
                        label = labelList[it].label,
                        color = labelList[it].color,
                        idLabel = labelList[it].idLabel,
                        selected = labelList[it].idLabel == selectedId,
                    ) { idLabel ->
                        selectedId = idLabel
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onDismiss
                ) {
                    Text(
                        text = "Cancelar",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = { onConfirm.invoke(selectedId) }
                ) {
                    Text(
                        text = "Aceptar",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun LabelItem(
    label: String,
    color: Color,
    selected: Boolean,
    idLabel: Int = 0,
    onChange: (Int) -> Unit
) {
    println("idLabel: $idLabel")
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Label,
            contentDescription = "Label para notas $label",
            tint = color)
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = label
        )
        Checkbox(
            checked = selected,
            onCheckedChange = {
                onChange.invoke(idLabel)
            }
        )
    }
}

