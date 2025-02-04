package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.raiserdev.demoproject.data.db.model.Usuario
import com.raiserdev.demoproject.data.domain.data.RegisterData
import com.raiserdev.demoproject.ui.state.FieldState
import com.raiserdev.demoproject.utils.showToast
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.register_birth_date
import demoprojectusc.composeapp.generated.resources.register_email
import demoprojectusc.composeapp.generated.resources.register_father_last_name
import demoprojectusc.composeapp.generated.resources.register_mother_last_name
import demoprojectusc.composeapp.generated.resources.register_name
import demoprojectusc.composeapp.generated.resources.register_nick_name
import demoprojectusc.composeapp.generated.resources.register_password
import demoprojectusc.composeapp.generated.resources.register_phone_number
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

class RegisterViewModel: ViewModel() {

    companion object {
        private const val MAX_DATE_LENGTH = 8
        private const val MAX_VALIDATE_TEXT = 3
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
    private val _birthDate = MutableStateFlow(FieldState())
    private val _email = MutableStateFlow("")
    private val _phoneNumber = MutableStateFlow("")
    private val _password = MutableStateFlow(Pair("",""))
    private val _confirmPassword = MutableStateFlow("")

    val nameFieldState: StateFlow<FieldState> = _nameFieldState

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

    private fun validateBirthDayTate(text: String) {
        when {
            text.isEmpty() -> {
                _birthDateFieldState.value = _birthDateFieldState.value.copy(
                    text = text,
                    isError = true,
                    errorMessage = "BirthDate requerido."
                )
            }
        }
        parseDdMmYyyy(text)
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


    /*fun onBirthDateChange(newBirthDate: String) {
        _birthDate.value = newBirthDate
    }*/
    fun onEmailChange(newEmail: String) { _email.value = newEmail }
    fun onPhoneNumberChange(phoneNumber: String) { _phoneNumber.value = phoneNumber }

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
            onValueChanged = ::validateBirthDayTate
        ),
        RegisterData.Text(
            title = Res.string.register_email,
            icon = emailIcon,
            length = 50,
            fieldState = email,
            onValueChanged = ::onEmailChange
        ),
        RegisterData.Text(
            title = Res.string.register_phone_number,
            icon = phoneNumberIcon,
            length = 20,
            fieldState = phoneNumber,
            onValueChanged = ::onPhoneNumberChange
        ),
        RegisterData.Auth(
            title = Res.string.register_password,
            icon = passwordIcon,
            length = 8,
            currentValue = password,
            onValueChanged = ::onPasswordChange
        ),
    )

    private fun parseDdMmYyyy(dateString: String): LocalDate? {
        // Verificamos que tenga 3 partes: DD, MM, YYYY
        val parts = dateString.split("/")
        if (parts.size != 3) return null

        val day = parts[0].toIntOrNull() ?: return null
        val month = parts[1].toIntOrNull() ?: return null
        val year = parts[2].toIntOrNull() ?: return null

        println("day $day month $month year $year")

        // Validaciones de rango
        if (day !in 1..31) return null
        if (month !in 1..12) return null

        // Crea el LocalDate; si la combinación día-mes-año es inválida, lanza excepción
        return try {
            LocalDate(year, month, day)
        } catch (e: IllegalArgumentException) {
            // Por ejemplo, si es 30 de Feb o 31 de Nov, etc.
            e.printStackTrace()
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
            isSucces.invoke(true)
        }

    }
}