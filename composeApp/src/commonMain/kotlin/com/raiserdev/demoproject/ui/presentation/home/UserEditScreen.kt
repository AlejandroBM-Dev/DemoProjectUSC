package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.raiserdev.demoproject.data.domain.data.RegisterData
import com.raiserdev.demoproject.ui.common.bar.UserEditTopAppBar
import com.raiserdev.demoproject.utils.showToast
import com.raiserdev.demoproject.utils.transformation.DateTransformation
import com.raiserdev.demoproject.utils.transformation.PhoneMexTransformation
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.accept
import demoprojectusc.composeapp.generated.resources.formatDate
import demoprojectusc.composeapp.generated.resources.help
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserEditScreen(
    userEditVM: UserEditViewModel,
    onBackClick: () -> Unit,
    onSaveChangesClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            UserEditTopAppBar(
                title = "Editar usuario",
                onBack = {
                    onBackClick.invoke()
                }
            )
        },
        bottomBar = {

        }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            UserEditHead(
                userEditVM = userEditVM
            )
            UserEditBody(
                userEditVM = userEditVM
            )
            UserEditFoot(
                userEditVM = userEditVM,
                onSaveEditClick = { onSaveChangesClick.invoke() },
                onCanceClick = { onBackClick.invoke() }
            )
        }
    }
}

@Composable
fun UserEditHead(
    userEditVM: UserEditViewModel,
) {
    val userData = userEditVM.user.collectAsState()
    val userDataName = "${userData.value?.userName} ${userData.value?.userFathersName} ${userData.value?.userMothersName}"
    Box(
        modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.20f)
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.width(90.dp).height(90.dp),
                imageVector = Icons.Default.SmartToy,
                contentDescription = null
            )
            /*Row {
                TextField(
                    value = userDataName,
                    onValueChange = { showToast("change... $it") },
                    modifier = Modifier.wrapContentWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                    )
                )
                IconButton(
                    onClick = { showToast("click...") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar nombre de usuario."
                    )
                }
            }*/
        }
    }
}

@Composable
fun UserEditBody(
    userEditVM: UserEditViewModel,
) {
    val dataEdit = userEditVM.fields
    val focusManager = LocalFocusManager.current
    val modifierLazyColumn = Modifier.fillMaxSize(1f)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {

        items(dataEdit) { data ->
            when (data) {
                is RegisterData.Text -> {
                    val fieldState by data.fieldState.collectAsState()
                    OutlinedTextField(
                        leadingIcon = {
                            androidx.compose.material.Icon(
                                imageVector = data.icon,
                                contentDescription = "${stringResource(data.title)} input text."
                            )
                        },
                        isError = fieldState.isError ,
                        supportingText = {
                            if (fieldState.isError) {
                                androidx.compose.material.Text(fieldState.errorMessage ?: "")
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
                        label = { androidx.compose.material.Text(text = stringResource(data.title)) },
                        modifier = modifierLazyColumn,
                        maxLines = 1
                    )
                }
                is RegisterData.Date -> {
                    val fieldState by data.fieldState.collectAsState()
                    OutlinedTextField(
                        value = fieldState.text,
                        leadingIcon = {
                            androidx.compose.material.Icon(
                                imageVector = data.icon,
                                contentDescription = "${stringResource(data.title)} input text."
                            )
                        },
                        isError = fieldState.isError ,
                        supportingText = {
                            if (fieldState.isError) {
                                androidx.compose.material.Text(fieldState.errorMessage ?: "")
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.moveFocus(FocusDirection.Next) }
                        ),
                        onValueChange = { if (it.length <= data.length) data.onValueChanged(it) },
                        label = {
                            androidx.compose.material.Text(
                                text = "${stringResource(data.title)} ${
                                    stringResource(
                                        Res.string.formatDate
                                    )
                                }"
                            )
                        },
                        modifier = modifierLazyColumn,
                        visualTransformation = DateTransformation(),
                        maxLines = 1
                    )
                }
                is RegisterData.Phone -> {
                    val fieldState by data.fieldState.collectAsState()
                    println("fieldState: ${fieldState.text}")
                    OutlinedTextField(
                        leadingIcon = {
                            androidx.compose.material.Icon(
                                imageVector = data.icon,
                                contentDescription = "${stringResource(data.title)} input text."
                            )
                        },
                        isError = fieldState.isError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        value = fieldState.text,
                        onValueChange = { input -> data.onValueChanged(input) },
                        label = { androidx.compose.material.Text(text = "${stringResource(data.title)}") },
                        modifier = modifierLazyColumn,
                        visualTransformation = PhoneMexTransformation(),
                        maxLines = 1
                    )
                }
                is RegisterData.Hex -> Unit //Sin uso por ahora
                is RegisterData.Numeric -> Unit //Sin uso por ahora
                is RegisterData.Auth -> TODO( "NO EXISTE PARA ESTE CASO" )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun UserEditFoot(
    userEditVM: UserEditViewModel,
    onSaveEditClick: () -> Unit,
    onCanceClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 0.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.4f).weight(0.9f),
            onClick = onCanceClick
        ) {
            androidx.compose.material.Text(
                stringResource(Res.string.help),
            )
        }
        Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre botones
        Button(
            modifier = Modifier.fillMaxWidth(0.4f).weight(0.9f),
            onClick = {
                showToast("User edit click...")
            }
        ) {
            androidx.compose.material.Text(
                stringResource(Res.string.accept),
            )
        }
    }
}
