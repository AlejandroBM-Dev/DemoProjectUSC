package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = paddingValues.calculateTopPadding(), bottom = 16.dp),
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
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val modifierLazyColumn = Modifier.fillMaxSize(1f)

        items(dataRecord) { data ->
            when (data) {
                is RegisterData.Text -> {
                    val fieldState by data.fieldState.collectAsState()
                    OutlinedTextField(
                        leadingIcon = {
                            Icon(
                                imageVector = data.icon,
                                contentDescription = "${stringResource(data.title)} input text."
                            )
                        },
                        isError = fieldState.isError ,
                        supportingText = {
                            if (fieldState.isError) {
                                Text(fieldState.errorMessage ?: "")
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        value = fieldState.text,
                        onValueChange = { if (it.length <= data.length) data.onValueChanged(it) },
                        label = { Text(text = stringResource(data.title)) },
                        modifier = modifierLazyColumn,
                        maxLines = 1
                    )
                }
                is RegisterData.Date -> {
                    val currentValue by data.currentValue.collectAsState()
                    OutlinedTextField(
                        value = currentValue,
                        leadingIcon = {
                            Icon(
                                imageVector = data.icon,
                                contentDescription = "${stringResource(data.title)} input text."
                            )
                        },
                        isError = currentValue.isEmpty() ,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        onValueChange = { if (it.length <= data.length) data.onValueChanged(it) },
                        label = { Text(text = "${stringResource(data.title)} ${stringResource(Res.string.formatDate)}") },
                        modifier = modifierLazyColumn,
                        visualTransformation = DateTransformation(),
                        maxLines = 1
                    )
                }
                is RegisterData.Phone -> {
                    val currentValue by data.currentValue.collectAsState()
                    OutlinedTextField(
                        value = currentValue,
                        leadingIcon = {
                            Icon(
                                imageVector = data.icon,
                                contentDescription = "${stringResource(data.title)} input text."
                            )
                        },
                        isError = currentValue.isNotEmpty(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = { input -> data.onValueChanged(input.filter { it.isDigit() }.take(8)) },
                        label = { Text(text = "${stringResource(data.title)} ${stringResource(Res.string.formatDate)}") },
                        modifier = modifierLazyColumn,
                        visualTransformation = DateTransformation(),
                        maxLines = 1
                    )
                }
                is RegisterData.Hex -> Unit //Sin uso por ahora
                is RegisterData.Numeric -> Unit //Sin uso por ahora
                is RegisterData.Auth -> {
                    val currentValue by data.currentValue.collectAsState()
                    var isTextVisible by remember { mutableStateOf(false) }
                    var isSecondTextVisible by remember { mutableStateOf(false) }
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.LightGray)
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        OutlinedTextField(
                            value = currentValue.first,
                            leadingIcon = {
                                Icon(
                                    imageVector = data.icon,
                                    contentDescription = "${stringResource(data.title)} input text."
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { isTextVisible = !isTextVisible }) {
                                    Icon(
                                        imageVector = if (isTextVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = if (isTextVisible) "Hide text" else "Show text"
                                    )
                                }
                            },
                            visualTransformation = if (isTextVisible) VisualTransformation.None else PasswordVisualTransformation(),

                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.moveFocus(FocusDirection.Next) }
                            ),
                            onValueChange = { if (it.length <= data.length) data.onValueChanged(it) },
                            label = { Text(text = stringResource(data.title)) },
                            modifier = modifierLazyColumn,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = currentValue.second,
                            leadingIcon = {
                                Icon(
                                    imageVector = data.icon,
                                    contentDescription = "${stringResource(data.title)} input text."
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { isSecondTextVisible = !isSecondTextVisible }) {
                                    Icon(
                                        imageVector = if (isSecondTextVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = if (isSecondTextVisible) "Hide text" else "Show text"
                                    )
                                }
                            },
                            visualTransformation = if (isSecondTextVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.moveFocus(FocusDirection.Next) }
                            ),
                            onValueChange = { if (it.length <= data.length) registerViewModel.onSecondPasswordChange(it)},
                            label = { Text(text = stringResource(data.title)) },
                            modifier = modifierLazyColumn,
                            maxLines = 1
                        )
                    }

                }
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
    Row(
        modifier = Modifier.wrapContentHeight().fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.4f).weight(0.9f),
            onClick = onRegisterSuccess
        ) {
            Text(
                "RegisterSuccess"
            )
        }
        Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre botones
        Button(
            modifier = Modifier.fillMaxWidth(0.4f).weight(0.9f),
            onClick = onHelpClick
        ) {
            Text("Help")
        }
    }

}