package com.raiserdev.demoproject.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.data.db.model.Usuario
import com.raiserdev.demoproject.data.domain.UserFieldsProvider
import com.raiserdev.demoproject.data.domain.data.RegisterData
import com.raiserdev.demoproject.data.ds.UserPreferencesRepository
import com.raiserdev.demoproject.domain.UserRepository
import com.raiserdev.demoproject.ui.state.FieldState
import com.raiserdev.demoproject.utils.EMAIL_REGEX
import com.raiserdev.demoproject.utils.MAX_VALIDATE_TEXT
import com.raiserdev.demoproject.utils.parseDdMmYyyy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserEditViewModel(
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
): ViewModel() {

    init {
        getUser()
    }

    private val _editUser = MutableStateFlow(false)
    val editUser: StateFlow<Boolean> get() = _editUser

    private val _nameFieldState = MutableStateFlow(FieldState())
    private val _nickNameFieldState = MutableStateFlow(FieldState())
    private val _fatherLastNameFieldState = MutableStateFlow(FieldState())
    private val _motherLastNameFieldState = MutableStateFlow(FieldState())
    private val _birthDateFieldState = MutableStateFlow(FieldState())
    private val _phoneFieldState = MutableStateFlow(FieldState())
    private val _emailFieldState = MutableStateFlow(FieldState())

    val fields: List<RegisterData> get() = _fields

    val name: StateFlow<FieldState> get() = _nameFieldState
    val nickName: StateFlow<FieldState> get() = _nickNameFieldState
    val fatherLastName: StateFlow<FieldState> get() = _fatherLastNameFieldState
    val motherLastName: StateFlow<FieldState> get() = _motherLastNameFieldState
    val birthDate: StateFlow<FieldState> get() = _birthDateFieldState

    val email: StateFlow<FieldState> get() = _emailFieldState
    val phoneNumber: StateFlow<FieldState> get() = _phoneFieldState

    private val _user = MutableStateFlow<Usuario?>(null)
    val user: MutableStateFlow<Usuario?> get() = _user

    private fun getUser() {
        viewModelScope.launch {
            val userDataStore = userPreferencesRepository.userPrefData.first()
            _user.value = userRepository.getUserById(userDataStore.userId)

            user.value?.let {
                _nickNameFieldState.value = _nickNameFieldState.value.copy(
                    text = it.nickname ?: "",
                    isError = false,
                    errorMessage = null
                )
                _nameFieldState.value = _nameFieldState.value.copy(
                    text = it.userName ?: "",
                    isError = false,
                    errorMessage = null
                )
                _fatherLastNameFieldState.value = _fatherLastNameFieldState.value.copy(
                    text = it.userFathersName ?: "",
                    isError = false,
                    errorMessage = null
                )
                _motherLastNameFieldState.value = _motherLastNameFieldState.value.copy(
                    text = it.userMothersName ?: "",
                    isError = false,
                    errorMessage = null
                )
                _birthDateFieldState.value = _birthDateFieldState.value.copy(
                    text = it.birthDate ?: "",
                    isError = false,
                    errorMessage = null
                )
                _emailFieldState.value = _emailFieldState.value.copy(
                    text = it.email ?: "",
                    isError = false,
                    errorMessage = null
                )
                _phoneFieldState.value = _phoneFieldState.value.copy(
                    text = it.numeroTelefonico ?: "",
                    isError = false,
                    errorMessage = null
                )
            }



        }
    }

    private fun validateNickNameText(text: String) {
        when {
            text.isEmpty() -> {
                _nickNameFieldState.value = _nickNameFieldState.value.copy(
                    text = text,
                    isError = true,
                    errorMessage = "Campo requerido."
                )
            }
            text.length <= MAX_VALIDATE_TEXT -> {
                _nickNameFieldState.value = _nickNameFieldState.value.copy(
                    text = text,
                    isError = true,
                    errorMessage = "Debe tener más de $MAX_VALIDATE_TEXT caracteres."
                )
            }
            else -> {
                _nickNameFieldState.value = _nickNameFieldState.value.copy(
                    text = text,
                    isError = false,
                    errorMessage = null
                )
            }
        }
    }

    private fun validateNameText(text: String) {
        when {
            text.isEmpty() -> {
                _nameFieldState.value = _nameFieldState.value.copy(
                    text = text,
                    isError = true,
                    errorMessage = "Campo requerido."
                )
            }
            text.length <= MAX_VALIDATE_TEXT -> {
                _nameFieldState.value = _nameFieldState.value.copy(
                    text = text,
                    isError = true,
                    errorMessage = "Debe tener más de $MAX_VALIDATE_TEXT caracteres."
                )
            }
            else -> {
                _nameFieldState.value = _nameFieldState.value.copy(
                    text = text,
                    isError = false,
                    errorMessage = null
                )
            }
        }
    }

    private fun validateFathersLastNameText(text: String) {
        when {
            text.isEmpty() -> {
                _fatherLastNameFieldState.value = _fatherLastNameFieldState.value.copy(
                    text = text,
                    isError = true,
                    errorMessage = "Campo requerido."
                )
            }
            text.length <= MAX_VALIDATE_TEXT -> {
                _fatherLastNameFieldState.value = _fatherLastNameFieldState.value.copy(
                    text = text,
                    isError = true,
                    errorMessage = "Debe tener más de $MAX_VALIDATE_TEXT caracteres."
                )
            }
            else -> {
                _fatherLastNameFieldState.value = _fatherLastNameFieldState.value.copy(
                    text = text,
                    isError = false,
                    errorMessage = null
                )
            }
        }
    }

    private fun validateMothersLastNameText(text: String) {
        when {
            text.isEmpty() -> {
                _motherLastNameFieldState.value = _motherLastNameFieldState.value.copy(
                    text = text,
                    isError = true,
                    errorMessage = "Campo requerido."
                )
            }
            text.length <= MAX_VALIDATE_TEXT -> {
                _motherLastNameFieldState.value = _motherLastNameFieldState.value.copy(
                    text = text,
                    isError = true,
                    errorMessage = "Debe tener más de $MAX_VALIDATE_TEXT caracteres."
                )
            }
            else -> {
                _motherLastNameFieldState.value = _motherLastNameFieldState.value.copy(
                    text = text,
                    isError = false,
                    errorMessage = null
                )
            }
        }
    }

    private fun validateBirthDayDate(text: String) {
        val parse = parseDdMmYyyy(text)
        if (parse.isNullOrEmpty()) {
            when {
                text.isEmpty() -> {
                    _birthDateFieldState.value = _birthDateFieldState.value.copy(
                        text = text,
                        isError = true,
                        errorMessage = "BirthDate requerido."
                    )
                }

                else -> {
                    _birthDateFieldState.value = _birthDateFieldState.value.copy(
                        text = text,
                        isError = false,
                        errorMessage = null
                    )
                }
            }
        } else {
            println(" parse_ $parse")
            _birthDateFieldState.value = _birthDateFieldState.value.copy(
                text = text,
                isError = true,
                errorMessage = parse
            )
        }
    }

    fun validateEmailChange(newEmail: String)  {
        if (newEmail.isBlank()) {
            _emailFieldState.value = _emailFieldState.value.copy(
                text = newEmail,
                isError = true,
                errorMessage = "El correo no puede estar vacío"
            )
            return
        }

        if (!EMAIL_REGEX.matches(newEmail)) {
            _emailFieldState.value = _emailFieldState.value.copy(
                text = newEmail,
                isError = true,
                errorMessage = "Formato de correo inválido"
            )
            return
        }

        // 3. Si todo está bien, actualizamos el estado del campo sin error
        _emailFieldState.value = _emailFieldState.value.copy(
            text = newEmail,
            isError = false,
            errorMessage = null
        )
    }

    fun validPhoneNumberChange(phoneNumber: String) {
        if (phoneNumber.isBlank()) {
            _phoneFieldState.value = _phoneFieldState.value.copy(
                text = phoneNumber,
                isError = true,
                errorMessage = "El número de teléfono no puede estar vacío"
            )
            return
        }
        if (phoneNumber.length < 10) {
            _phoneFieldState.value = _phoneFieldState.value.copy(
                text = phoneNumber,
                isError = true,
                errorMessage = "El número de teléfono debe tener al menos 10 dígitos"
            )
            return
        }
        _phoneFieldState.value = _phoneFieldState.value.copy(
            text = phoneNumber,
            isError = false,
            errorMessage = null
        )

    }

    fun editUser(enable: Boolean) {
        _editUser.value = enable
    }

    private val _fields: List<RegisterData> = UserFieldsProvider.provideFields(
        nickName = nickName,
        name = name,
        fatherLastName = fatherLastName,
        motherLastName = motherLastName,
        birthDate = birthDate,
        email = email,
        phoneNumber = phoneNumber,
        null,
        onNickNameChange = ::validateNickNameText,
        onNameChange = ::validateNameText,
        onFatherLastNameChange = ::validateFathersLastNameText,
        onMotherLastNameChange = ::validateMothersLastNameText,
        onBirthDateChange = ::validateBirthDayDate,
        onEmailChange = ::validateEmailChange,
        onPhoneChange = ::validPhoneNumberChange,
        onPasswordChange = null
    )

}