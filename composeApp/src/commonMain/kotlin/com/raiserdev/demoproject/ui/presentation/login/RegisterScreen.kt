package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.raiserdev.demoproject.data.domain.data.RegisterData
import com.raiserdev.demoproject.utils.transformation.DateTransformation
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.formatDate
import demoprojectusc.composeapp.generated.resources.register_title
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
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
    Text(
        text = stringResource(Res.string.register_title),
        style = MaterialTheme.typography.h3,
    )
}

@Composable
fun bodyView(
    registerViewModel: RegisterViewModel
) {

    val dataRecord = registerViewModel.fields
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val modifierLazyColumn = Modifier.fillMaxSize(1f)

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
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        value = currentValue,
                        onValueChange = { if (it.length <= data.length) data.onValueChanged(it) },
                        label = { Text(text = stringResource(data.title)) },
                        modifier = modifierLazyColumn,
                        maxLines = 1
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
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        value = currentValue,
                        onValueChange = { if (it.length <= data.length) data.onValueChanged(it) },
                        label = { Text(text = "${stringResource(data.title)} ${stringResource(Res.string.formatDate)}") },
                        modifier = modifierLazyColumn,
                        visualTransformation = DateTransformation(),
                        maxLines = 1
                    )
                }
                is RegisterData.Phone -> {
                    val currentValue by data.currentValue.collectAsState()
                    TextField(
                        leadingIcon = {
                            Icon(
                                imageVector = data.icon,
                                contentDescription = "${stringResource(data.title)} input text."
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        value = currentValue,
                        onValueChange = { input -> data.onValueChanged(input.filter { it.isDigit() }.take(8)) },
                        label = { Text(text = "${stringResource(data.title)} ${stringResource(Res.string.formatDate)}") },
                        modifier = modifierLazyColumn,
                        visualTransformation = DateTransformation(),
                        maxLines = 1
                    )
                }
                is RegisterData.Hex -> Unit //Sin uso por ahora
                is RegisterData.Numeric -> Unit //Sin uso por ahora
                is RegisterData.Auth -> Unit //Sin uso por ahora
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