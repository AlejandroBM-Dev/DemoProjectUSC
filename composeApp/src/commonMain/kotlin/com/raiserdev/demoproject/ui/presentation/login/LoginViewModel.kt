package com.raiserdev.demoproject.ui.presentation.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _credentials = MutableStateFlow(Pair("", ""))

    val credentials: StateFlow<Pair<String, String>> get() = _credentials

    fun onUsernameChange(newUsername: String) {
        _credentials.value = _credentials.value.copy(first = newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        _credentials.value = _credentials.value.copy(second = newPassword)
    }
}