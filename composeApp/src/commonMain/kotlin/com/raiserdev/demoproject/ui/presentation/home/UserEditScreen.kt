package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UserEditScreen(
    userEditVM: UserEditViewModel,
    onBackClick: () -> Unit,
    onSaveChangesClick: () -> Unit,
) {
    Scaffold { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            UserEditHead()
            UserEditBody()
            UserEditFoot()
        }
    }
}

@Composable
fun UserEditHead() {
    Box(
        modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.3f)
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.width(150.dp).height(150.dp),
            imageVector = Icons.Default.Image,
            contentDescription = null
        )
    }
}

@Composable
fun UserEditBody() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(
            "Hola!!!"
        )

        Text(
            "Hola!!!x2"
        )
    }
}

@Composable
fun UserEditFoot() {

}

@Composable
@Preview
fun UserEditScreenPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        UserEditHead()
        UserEditBody()
        UserEditFoot()
    }
}