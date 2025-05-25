package com.raiserdev.demoproject.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raiserdev.demoproject.data.ItemNoteData

@Composable
fun ItemNote(
    modifier: Modifier,
    data: ItemNoteData,
    onClickEdit: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = data.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                )
            }

            Text(
                text = data.createdDate,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(15.dp))

            Row {
                Text(
                    text = data.message,
                    maxLines = 3,
                    fontSize = 15.sp
                )
            }

            Spacer(Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        onClickEdit.invoke()
                    }
                ) {
                    Icon( imageVector = Icons.Filled.Edit, contentDescription = "Edit note." )
                }
            }
        }
    }
}

@Composable
fun NewItemNotee(
    modifier: Modifier,
    onClickAddNote: () -> Unit
){
    ElevatedButton(
        onClick = { onClickAddNote.invoke() },
        modifier = modifier.fillMaxWidth().height(140.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column{
            Text(
                text = "Agregar nota",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(5.dp))
            Icon( imageVector = Icons.Filled.Add, contentDescription = "Edit note.", modifier = Modifier.width(75.dp).height(75.dp), tint = Color.Black )

        }
    }
}
