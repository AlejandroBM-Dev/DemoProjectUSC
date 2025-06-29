package com.raiserdev.demoproject.ui.presentation.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.data.ds.PrefsDataStore
import com.raiserdev.demoproject.data.ds.UserPreferencesRepository
import com.raiserdev.demoproject.domain.UserRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.getScopeName

class LoginViewModel(
    private val repository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {


    private val _credentials = MutableStateFlow(Pair("", ""))
    val credentials: StateFlow<Pair<String, String>> get() = _credentials.asStateFlow()

    private val _showPassword = MutableStateFlow(false)
    val showPassword: StateFlow<Boolean> get() = _showPassword.asStateFlow()

    private val _rememberCredentials = MutableStateFlow(false)
    val rememberCredentials: StateFlow<Boolean> get() = _rememberCredentials.asStateFlow()



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

    fun onRememberCredentials(remember: Boolean) {
        _rememberCredentials.value = remember
    }



    fun isLogged(onLogin: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isLogged = userPreferencesRepository.userPrefData.first()
            println("isLogged: $isLogged")
            onLogin.invoke(isLogged.isLoggedIn)
        }
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
                if (userLogin != null) {
                    userPreferencesRepository.updateUserName(userLogin.userName ?: "")
                    userPreferencesRepository.updateEmail(userLogin.email ?: "")
                    userPreferencesRepository.setLoggedIn(rememberCredentials.value)
                    //se valida el cambio de estado para isLoggedIn...

                    println("userLogin: $userLogin")
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