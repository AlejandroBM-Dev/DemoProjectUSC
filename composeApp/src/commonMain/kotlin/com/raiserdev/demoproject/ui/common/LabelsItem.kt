package com.raiserdev.demoproject.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raiserdev.demoproject.data.db.model.LabelsData
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LabelItem(
    modifier: Modifier = Modifier,
    data: LabelsData = LabelsData(-1, 0,"Label", 0, color = Color.Red),
    onClick: (Int) -> Unit,
){
    Card(
        modifier = modifier.wrapContentSize(),
        colors = CardDefaults.cardColors(containerColor = data.color),
        onClick = {
            onClick.invoke(data.idLabel)
        }
    ) {
        Row(
            modifier = modifier
                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = data.label,
                fontSize = 15.sp,
                color = if (data.color == Color.White || data.color == Color.Yellow) Color.Black else Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier.defaultMinSize(minWidth = 16.dp, minHeight = 16.dp).background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = data.count.toString(),
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }

    }
}

@Composable
@Preview()
fun LabelItemPreview(){
    LabelItem(
        onClick = {}
    )
}