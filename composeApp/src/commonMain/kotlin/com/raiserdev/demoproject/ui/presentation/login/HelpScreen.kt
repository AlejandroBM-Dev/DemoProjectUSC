package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.raiserdev.demoproject.ui.common.NotesTopAppBar
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.help_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(onBack: () -> Unit) {
    val helpViewModel = koinViewModel<HelpViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize().fillMaxSize(),
        topBar = {
            NotesTopAppBar(
                title = stringResource(Res.string.help_title),
                onBack = onBack ,
                onSettingsClick = { Unit }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Head()

            Body(
                onBack = onBack
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Head() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.LightGray),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp)
                        .size(100.dp)
                        .clip(CircleShape),
                    model = Res.getUri("drawable/profile_.png"),
                    contentDescription = "HelperImage",
                    contentScale = ContentScale.Crop
                )
            }
            Column {
                Text(
                    text = "Proyecto:"
                )
                Text(
                    text = "Notas.",
                    modifier = Modifier.padding(start = 15.dp)
                )

                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Desarrollado encargado: "
                )
                Text(
                    text = "Alejandro Bautista M.",
                    modifier = Modifier.padding(start = 15.dp)
                )

                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Ubicación:"
                )
                Text(
                    text = "Edo. de México ó Querétaro",
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        }

        LocationText()

        SpeakText()

    }

}

@Composable
fun LocationText() {
    Spacer(modifier = Modifier.size(10.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0xFFE0F7FA), shape = RoundedCornerShape(12.dp)) // Fondo celeste y bordes redondeados
            .padding(horizontal = 12.dp, vertical = 6.dp) // Espaciado interno
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Ubicación",
            tint = Color.Red
        )
        Text(
            text = "Mexico, Querétaro"
        )
    }
}

@Composable
fun SpeakText() {
    Spacer(modifier = Modifier.size(10.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0xFFE0F7FA), shape = RoundedCornerShape(12.dp)) // Fondo celeste y bordes redondeados
            .padding(horizontal = 12.dp, vertical = 6.dp) // Espaciado interno
    ) {
        Icon(
            imageVector = Icons.Filled.Speaker,
            contentDescription = "Speak",
            tint = Color.Red
        )
        Text(
            text = "Spannish (Advance) / English (Beginner)"
        )
    }
}

@Composable
fun Body(
    onBack: () -> Unit
) {

    Button(
        onClick = onBack
    ) {
        Text("Back")
    }
}

@Preview
@Composable
fun HelpScreenPreview() {
    HelpScreen(onBack = {})
}