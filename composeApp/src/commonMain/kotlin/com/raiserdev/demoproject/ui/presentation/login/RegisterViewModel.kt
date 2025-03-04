package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Smartphone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.data.db.model.Usuario
import com.raiserdev.demoproject.data.domain.data.RegisterData
import com.raiserdev.demoproject.domain.NotasRepository
import com.raiserdev.demoproject.ui.state.FieldState
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.register_birth_date
import demoprojectusc.composeapp.generated.resources.register_email
import demoprojectusc.composeapp.generated.resources.register_father_last_name
import demoprojectusc.composeapp.generated.resources.register_mother_last_name
import demoprojectusc.composeapp.generated.resources.register_name
import demoprojectusc.composeapp.generated.resources.register_nick_name
import demoprojectusc.composeapp.generated.resources.register_password
import demoprojectusc.composeapp.generated.resources.register_phone_number
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.coroutines.coroutineContext

class RegisterViewModel(
    private val repository: NotasRepository
): ViewModel() {

    companion object {
        private const val MAX_DATE_LENGTH = 8
        private const val MAX_VALIDATE_TEXT = 3
        private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9]+$".toRegex()

    }

    private val nameIcon = Icons.Default.Face
    private val nickNameIcon = Icons.Default.SmartToy
    private val calendarIcon = Icons.Default.Event
    private val emailIcon = Icons.Default.AlternateEmail
    private val phoneNumberIcon = Icons.Default.Smartphone
    private val passwordIcon = Icons.Default.Lock

    val fields: List<RegisterData> get() = _fields //Todos mis campos que se agregaran en el registro.

    private val _nameFieldState = MutableStateFlow(FieldState())
    private val _nickNameFieldState = MutableStateFlow(FieldState())
    private val _fatherLastNameFieldState = MutableStateFlow(FieldState())
    private val _motherLastNameFieldState = MutableStateFlow(FieldState())
    private val _birthDateFieldState = MutableStateFlow(FieldState())
    private val _phoneFieldState = MutableStateFlow(FieldState())

    private val _emailFieldState = MutableStateFlow(FieldState())
    private val _password = MutableStateFlow(Pair("",""))

    val name: StateFlow<FieldState> get() = _nameFieldState
    val nickName: StateFlow<FieldState> get() = _nickNameFieldState
    val fatherLastName: StateFlow<FieldState> get() = _fatherLastNameFieldState
    val motherLastName: StateFlow<FieldState> get() = _motherLastNameFieldState
    val birthDate: StateFlow<FieldState> get() = _birthDateFieldState

    val email: StateFlow<FieldState> get() = _emailFieldState
    val phoneNumber: StateFlow<FieldState> get() = _phoneFieldState
    val password: StateFlow<Pair<String,String>> get() = _password


    // Lógica de validación
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


    fun validateEmailChange(newEmail: String)  {
        // 1. Verificar si está vacío
        if (newEmail.isBlank()) {
            _emailFieldState.value = _emailFieldState.value.copy(
                text = newEmail,
                isError = true,
                errorMessage = "El correo no puede estar vacío"
            )
            return
        }

        // 2. Verificar formato usando Patterns de Android
        if (!emailRegex.matches(newEmail)) {
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
        println("number_ $phoneNumber")
        _phoneFieldState.value = _phoneFieldState.value.copy(
            text = phoneNumber,
            isError = false,
            errorMessage = null
        )
    }

    fun onPasswordChange(newPassword: String) {
        val validatePassword = _password.value.second
        _password.value = Pair(newPassword,validatePassword)
    }
    fun onSecondPasswordChange(validatePassword: String) {
        val newPassword = _password.value.first
        _password.value = Pair(newPassword,validatePassword)
    }

    fun showError(
        dateString: String,
        onShowError: (Boolean) -> Unit
    ) {
        if (dateString.length == MAX_DATE_LENGTH) {
            val date = parseDdMmYyyy(birthDate.value.text)
            if (date == null) {
                onShowError.invoke(true)
            } else {
                onShowError.invoke(false)
            }
        }
    }

    private val _fields = listOf(
        RegisterData.Text(
            title = Res.string.register_nick_name,
            icon = nickNameIcon,
            length = 12,
            fieldState = nickName,
            onValueChanged = ::validateNickNameText
        ),
        RegisterData.Text(
            title = Res.string.register_name,
            icon = nameIcon,
            length = 25,
            fieldState = name,
            onValueChanged = ::validateNameText
        ),
        RegisterData.Text(
            title = Res.string.register_father_last_name,
            icon = nameIcon,
            length = 25,
            fieldState = fatherLastName,
            onValueChanged = ::validateFathersLastNameText
        ),
        RegisterData.Text(
            title = Res.string.register_mother_last_name,
            icon = nameIcon,
            length = 25,
            fieldState = motherLastName,
            onValueChanged = ::validateMothersLastNameText
        ),
        RegisterData.Date(
            title = Res.string.register_birth_date,
            icon = calendarIcon,
            length = 10,
            fieldState = birthDate,
            onValueChanged = ::validateBirthDayDate
        ),
        RegisterData.Text(
            title = Res.string.register_email,
            icon = emailIcon,
            length = 50,
            fieldState = email,
            onValueChanged = ::validateEmailChange
        ),
        RegisterData.Phone(
            title = Res.string.register_phone_number,
            icon = phoneNumberIcon,
            length = 20,
            fieldState = phoneNumber,
            onValueChanged = ::validPhoneNumberChange
        ),
        RegisterData.Auth(
            title = Res.string.register_password,
            icon = passwordIcon,
            length = 8,
            currentValue = password,
            onValueChanged = ::onPasswordChange
        ),
    )

    private fun parseDdMmYyyy(dateString: String): String? {
        println("dateString: $dateString")

        // 1. Validar longitud (sin separadores => 8 caracteres)
        if (dateString.length != 8) return "Formato incorrecto"

        // 2. Extraer día, mes y año
        val day = dateString.substring(0, 2).toIntOrNull() ?: return "Día incorrecto"
        val month = dateString.substring(2, 4).toIntOrNull() ?: return "Mes incorrecto"
        val year = dateString.substring(4, 8).toIntOrNull() ?: return "Año incorrecto"
        println("day:$day _ month: $month _ year:$year")

        // 3. Revisar rangos básicos
        if (day !in 1..31) return "Dia incorrecto"
        if (month !in 1..12) return "Mes incorrecto"

        // 4. Construir LocalDate de kotlinx.datetime
        val date = try {
            LocalDate(year, month, day)
        } catch (e: IllegalArgumentException) {
            // Cae aquí si la combinación no existe (ej. 31/11/2023, 29/02 en año no bisiesto, etc.)
            return "Día incorrecto"
        }

        // 5. Comparar con la fecha actual (Clock.System.now())
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        return if (date > currentDate.date) {
            "Formato mayor al date actual"
        } else {
            null
        }
    }

    fun setRegister(isSucces: (Boolean) -> Unit){

        if (
            (nickName.value.isError || nickName.value.text.isEmpty()) ||
            (name.value.isError || name.value.text.isEmpty()) ||
            (fatherLastName.value.isError || fatherLastName.value.text.isEmpty()) ||
            (motherLastName.value.isError || motherLastName.value.text.isEmpty())
        ) {
            validateNameText(name.value.text)
            validateNickNameText(nickName.value.text)
            validateFathersLastNameText(fatherLastName.value.text)
            validateMothersLastNameText(motherLastName.value.text)

            isSucces.invoke(false)

        } else {
            viewModelScope.launch {
                repository.addUser(
                    Usuario(
                        id = 0,
                        nickname = nickName.value.text,
                        userName = name.value.text,
                        userFathersName = fatherLastName.value.text,
                        userMothersName = motherLastName.value.text,
                        birthDate = birthDate.value.text,
                        email = email.value.text,
                        numeroTelefonico = phoneNumber.value.text,
                        fechaCreacion = "",
                        fechaActualizacion = "",
                        password = password.value.first
                    )
                )

                isSucces.invoke(true)
            }
        }

    }
}