package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

    loginVM.isLogged { isLogged ->
        if (isLogged) {
            onLoginSuccess.invoke()
        } else {
            println("status... isLogged: $isLogged")
        }
    }

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
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(0.7f).clip(RoundedCornerShape(10.dp)),
            model = Res.getUri("files/login_update.png"),
            contentDescription = "LoginImage"
        )
    }

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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.login_title),
            style = MaterialTheme.typography.headlineMedium, // Tamaño de texto según Material Design
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp) // Espaciado debajo del título
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
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
        OutlinedTextField(
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
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Recordar")
            Checkbox(
                checked = loginVM.rememberCredentials.collectAsState().value,
                onCheckedChange = { loginVM.onRememberCredentials(it) }
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
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
        Spacer(modifier = Modifier.height(4.dp)) // Espaciado entre botones
        Text("or")
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = onRegisterClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
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


