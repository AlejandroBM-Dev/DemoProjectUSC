package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.raiserdev.demoproject.data.domain.data.RegisterData
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onHelpClick: () -> Unit
) {
    val registerVM = koinViewModel<RegisterViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            head()

            bodyView(
                registerVM
            )

            foot(
                onRegisterSuccess = onRegisterSuccess,
                onHelpClick = onHelpClick
            )

        }
    }
}

@Composable
fun head() {
    Text("Help Screen")
}

@Composable
fun bodyView(
    registerViewModel: RegisterViewModel
) {

    val dataRecord = registerViewModel.fields
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val modifierlazyColumn = Modifier.fillMaxSize(1f)

        items(dataRecord) { data ->
            when (data) {
                is RegisterData.Text -> {
                    val currentValue by data.currentValue.collectAsState()
                    TextField(
                        leadingIcon = {
                            Icon(
                                imageVector = data.icon,
                                contentDescription = "${stringResource(data.title)} input text."
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        value = currentValue,
                        onValueChange = { if (it.length <= data.length) data.onValueChanged(it) },
                        label = { Text(text = stringResource(data.title)) },
                        modifier = modifierlazyColumn, // Campo de texto ocupa el 80% del ancho
                    )
                }
                is RegisterData.Date -> {
                    val currentValue by data.currentValue.collectAsState()
                    TextField(
                        leadingIcon = {
                            Icon(
                                imageVector = data.icon,
                                contentDescription = "${stringResource(data.title)} input text."
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = currentValue,
                        onValueChange = { if (it.length <= data.length) data.onValueChanged(it) },
                        label = { Text(text = "${stringResource(data.title)} (DD/MM/YYYY)") },
                        modifier = modifierlazyColumn, // Campo de texto ocupa el 80% del ancho
                    )
                }
                is RegisterData.Hex -> {}
                is RegisterData.Numeric -> {}
                is RegisterData.Auth -> {}
                else -> { throw IllegalStateException("Invalid RegisterData type")}
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@Composable
fun foot(
    onRegisterSuccess: () -> Unit,
    onHelpClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones
    Button(
        onClick = onRegisterSuccess
    ) {
        Text("RegisterSuccess")
    }
    Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones
    Button(
        onClick = onHelpClick
    ) {
        Text("Help")
    }
}