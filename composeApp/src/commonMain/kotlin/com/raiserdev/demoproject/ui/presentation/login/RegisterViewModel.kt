package com.raiserdev.demoproject.ui.presentation.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.raiserdev.demoproject.data.domain.data.RegisterData
import demoprojectusc.composeapp.generated.resources.Res
import demoprojectusc.composeapp.generated.resources.error_date_format
import demoprojectusc.composeapp.generated.resources.error_exception
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
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

class RegisterViewModel: ViewModel() {

    companion object {
        private const val MAX_DATE_LENGTH = 8
    }
    private val nameIcon = Icons.Default.Face
    private val nickNameIcon = Icons.Default.SmartToy
    private val calendarIcon = Icons.Default.Event
    private val emailIcon = Icons.Default.AlternateEmail
    private val phoneNumberIcon = Icons.Default.Smartphone
    private val passwordIcon = Icons.Default.Lock

    // 3) Expose the list of fields (if needed) as a public property or Flow
    val fields: List<RegisterData> get() = _fields

    private val _name = MutableStateFlow("")
    private val _nickName = MutableStateFlow("")
    private val _fatherLastName = MutableStateFlow("")
    private val _motherLastName = MutableStateFlow("")
    private val _birthDate = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _phoneNumber = MutableStateFlow("")
    private val _password = MutableStateFlow(Pair("",""))
    private val _confirmPassword = MutableStateFlow("")

    val name: StateFlow<String> get() = _name
    val nickName: StateFlow<String> get() = _nickName
    val fatherLastName: StateFlow<String> get() = _fatherLastName
    val motherLastName: StateFlow<String> get() = _motherLastName
    val birthDate: StateFlow<String> get() = _birthDate
    val email: StateFlow<String> get() = _email
    val phoneNumber: StateFlow<String> get() = _phoneNumber
    val password: StateFlow<Pair<String, String>> get() = _password


    fun onNameChange(newName: String) { _name.value = newName }
    fun onNickNameChange(newNickName: String) { _nickName.value = newNickName }
    fun onFatherLastNameChange(newFatherLastName: String) { _fatherLastName.value = newFatherLastName }
    fun onMotherLastNameChange(newMotherLastName: String) { _motherLastName.value = newMotherLastName }
    fun onBirthDateChange(newBirthDate: String) {
        _birthDate.value = newBirthDate
    }
    fun onEmailChange(newEmail: String) { _email.value = newEmail }
    fun onPhoneNumberChange(phoneNumber: String) { _phoneNumber.value = phoneNumber }
    fun onPasswordChange(newPassword: String) { _password.value = _password.value.copy(first = newPassword) }
    fun onConfirmPasswordChange(newConfirmPassword: String) { _password.value = _password.value.copy(second = newConfirmPassword) }

    fun showError(
        dateString: String,
        onShowError: (Boolean) -> Unit
    ) {
        if (dateString.length == MAX_DATE_LENGTH) {
            val date = parseDdMmYyyy(_birthDate.value)
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
            currentValue = nickName,
            onValueChanged = ::onNickNameChange
        ),
        RegisterData.Text(
            title = Res.string.register_name,
            icon = nameIcon,
            length = 25,
            currentValue = name,
            onValueChanged = ::onNameChange
        ),
        RegisterData.Text(
            title = Res.string.register_father_last_name,
            icon = nameIcon,
            length = 25,
            currentValue = fatherLastName,
            onValueChanged = ::onFatherLastNameChange
        ),
        RegisterData.Text(
            title = Res.string.register_mother_last_name,
            icon = nameIcon,
            length = 25,
            currentValue = motherLastName,
            onValueChanged = ::onMotherLastNameChange
        ),
        RegisterData.Date(
            title = Res.string.register_birth_date,
            icon = calendarIcon,
            length = 10,
            currentValue = birthDate,
            onValueChanged = ::onBirthDateChange
        ),
        RegisterData.Text(
            title = Res.string.register_email,
            icon = emailIcon,
            length = 50,
            currentValue = email,
            onValueChanged = ::onEmailChange
        ),
        RegisterData.Text(
            title = Res.string.register_phone_number,
            icon = phoneNumberIcon,
            length = 20,
            currentValue = phoneNumber,
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
}