package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.raiserdev.demoproject.utils.showToast
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.login_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

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
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize(0.9f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.login_title),
            style = MaterialTheme.typography.h3, // Tamaño de texto según Material Design
            modifier = Modifier.padding(bottom = 16.dp) // Espaciado debajo del título
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = credentials.first,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Login username."
                )
            },
            onValueChange = { loginVM.onUsernameChange(it) },
            label = { Text("Username:") },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Acción cuando se presiona "Done"
                    focusManager.clearFocus() // Cierra el teclado
                }
            ),
            modifier = Modifier.fillMaxWidth(0.8f) // Campo de texto ocupa el 80% del ancho
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Login password."
                )
            },
            value = credentials.second,
            onValueChange = { loginVM.onPasswordChange(it) },
            label = { Text("Password:") },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Acción cuando se presiona "Done"
                    focusManager.clearFocus() // Cierra el teclado
                }
            ),
            modifier = Modifier.fillMaxWidth(0.8f), // Campo de texto ocupa el 80% del ancho
            visualTransformation = if(!loginVM.onHidePassword()) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { loginVM.onHidePassword()} ) {
                    Icon(
                        imageVector = if (loginVM.onHidePassword()) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Show Password"
                    )
                }
            }
        )
        /*
        * OutlinedTextField(
                value = viewModel.password,
                visualTransformation = if(!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                onValueChange = { viewModel.password = it },
                trailingIcon = { IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(imageVector = if(showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility, contentDescription = "Show Password")
                }})*/
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Recordar")
            Checkbox(
                checked = false,
                onCheckedChange = {  }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                loginVM.onLogin(
                    onSuccess = {
                        onLoginSuccess.invoke()
                    },
                    onError = { message ->
                        showToast(message)
                    }
                )
            },
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
}

@Composable
fun FootView(
    onHelpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {

        Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones
        Button(
            onClick = onHelpClick,
            shape = CircleShape,
            modifier = Modifier
                .size(50.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ContactSupport,
                contentDescription = "Login username."
            )
        }
    }
}
