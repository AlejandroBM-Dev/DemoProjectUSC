package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _credentials = MutableStateFlow(Pair("", ""))
    private val _showPassword = MutableStateFlow(false)

    val credentials: StateFlow<Pair<String, String>> get() = _credentials
    val showPassword: StateFlow<Boolean> get() = _showPassword

    fun onUsernameChange(newUsername: String) {
        _credentials.value = _credentials.value.copy(first = newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        _credentials.value = _credentials.value.copy(second = newPassword)
    }

    fun onHidePassword() : Boolean  {
        _showPassword.value = !_showPassword.value
       return showPassword.value
    }
}