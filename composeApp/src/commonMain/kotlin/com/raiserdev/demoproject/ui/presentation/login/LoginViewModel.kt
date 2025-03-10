package com.raiserdev.demoproject.ui.presentation.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.domain.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
): ViewModel() {

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

    fun onLogin(
        onSuccess: () -> Unit,
        onError:(message: String) -> Unit
    ){
        viewModelScope.launch {
            val validateEmailOrPhone = credentials.value.first
            val isValidPhoneOrEmail =
                if (validateEmailOrPhone.contains("@")) {
                    repository.existsByEmail(credentials.value.first)
                } else {
                    repository.existsByTelefono(credentials.value.first)
                }

            if (isValidPhoneOrEmail) {
                val userLogin = repository.validateUser(
                    credentials.value.first,
                    credentials.value.second
                )
                println("userLogin: $userLogin")
                if (userLogin != null) {
                    onSuccess.invoke()
                } else {
                    onError.invoke("El password no es correcto.")
                }
            } else {
                onError.invoke("Tú user name no existe en la db.")
            }

            //val response = repository.login(credentials.value.first, credentials.value.second)

        }
    }
}