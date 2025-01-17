package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import demoprojectusc.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen (
    loginVM: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    onHelpClick: () -> Unit,
){
    val credentials by loginVM.credentials.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), // Respeta el padding del Scaffold
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally // Alinea el contenido al centro horizontalmente
        ) {
            HeadView()
            BodyView(
                loginVM = loginVM,
                credentials = credentials,
                onLoginSuccess = onLoginSuccess,
                onRegisterClick = onRegisterClick
            )
            FootView(
                onHelpClick = onHelpClick
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HeadView() {
    AsyncImage(
        modifier = Modifier.wrapContentSize().padding(5.dp),
        model = Res.getUri("files/undraw_login.svg"),
        contentDescription = "LoginImage"
    )
}

@Composable
fun BodyView(
    loginVM: LoginViewModel,
    credentials: Pair<String, String>,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    Text(
        text = "Login",
        style = MaterialTheme.typography.h3, // Tamaño de texto según Material Design
        modifier = Modifier.padding(bottom = 16.dp) // Espaciado debajo del título
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
        value = credentials.first,
        onValueChange = { loginVM.onUsernameChange(it)  },
        label = { Text("Username:") },
        modifier = Modifier.fillMaxWidth(0.8f) // Campo de texto ocupa el 80% del ancho
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
        value = credentials.second,
        onValueChange = { loginVM.onPasswordChange(it)  },
        label = { Text("Password:") },
        modifier = Modifier.fillMaxWidth(0.8f) // Campo de texto ocupa el 80% del ancho
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(
        onClick = onLoginSuccess,
        modifier = Modifier.fillMaxWidth(0.8f) // Botón ocupa el 80% del ancho
    ) {
        Text(
            text = "Login",
        )
    }
    Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones
    Text("or")
    Button(
        onClick = onRegisterClick,
        modifier = Modifier.fillMaxWidth(0.8f) // Botón ocupa el 80% del ancho
    ) {
        Text("Register")
    }
}

@Composable
fun FootView(
    onHelpClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones
        Button(
            onClick = onHelpClick,
            modifier = Modifier.fillMaxWidth(0.8f) // Botón ocupa el 80% del ancho
        ) {
            Text("Help")
        }
    }
}
