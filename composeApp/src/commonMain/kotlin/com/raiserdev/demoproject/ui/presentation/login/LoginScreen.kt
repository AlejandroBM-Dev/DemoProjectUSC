package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen (
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    onHelpClick: () -> Unit,
){
    val loginVM = koinViewModel<LoginViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), // Respeta el padding del Scaffold
            verticalArrangement = Arrangement.Center, // Centra verticalmente el contenido
            horizontalAlignment = Alignment.CenterHorizontally // Alinea el contenido al centro horizontalmente
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .background(Color.Blue),
            )
            Text(
                text = "Login",
                style = MaterialTheme.typography.h5, // Tamaño de texto según Material Design
                modifier = Modifier.padding(bottom = 16.dp) // Espaciado debajo del título
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = loginVM.credentials.value.first,
                onValueChange = { loginVM.onUsernameChange(it)  },
                label = { Text("Username:") },
                modifier = Modifier.fillMaxWidth(0.8f) // Campo de texto ocupa el 80% del ancho
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = loginVM.credentials.value.second,
                onValueChange = { loginVM.onPasswordChange(it)  },
                label = { Text("Password:") },
                modifier = Modifier.fillMaxWidth(0.8f) // Campo de texto ocupa el 80% del ancho
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onLoginSuccess,
                modifier = Modifier.fillMaxWidth(0.8f) // Botón ocupa el 80% del ancho
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones
            Text("or")
            Button(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth(0.8f) // Botón ocupa el 80% del ancho
            ) {
                Text("Register")
            }
            Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones
            Button(
                onClick = onHelpClick,
                modifier = Modifier.fillMaxWidth(0.8f) // Botón ocupa el 80% del ancho
            ) {
                Text("Help")
            }
        }
    }
}

