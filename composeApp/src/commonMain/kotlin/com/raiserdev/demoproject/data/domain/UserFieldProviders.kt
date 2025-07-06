package com.raiserdev.demoproject.data.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Smartphone
import com.raiserdev.demoproject.data.domain.data.RegisterData
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
import kotlinx.coroutines.flow.StateFlow

object UserFieldsProvider {

    fun provideFields(
        nickName: StateFlow<FieldState>,
        name: StateFlow<FieldState>,
        fatherLastName: StateFlow<FieldState>,
        motherLastName: StateFlow<FieldState>,
        birthDate: StateFlow<FieldState>,
        email: StateFlow<FieldState>,
        phoneNumber: StateFlow<FieldState>,
        password: StateFlow<Pair<String, String>>? = null, // null si no se usa
        onNickNameChange: (String) -> Unit,
        onNameChange: (String) -> Unit,
        onFatherLastNameChange: (String) -> Unit,
        onMotherLastNameChange: (String) -> Unit,
        onBirthDateChange: (String) -> Unit,
        onEmailChange: (String) -> Unit,
        onPhoneChange: (String) -> Unit,
        onPasswordChange: ((String) -> Unit)? = null
    ): List<RegisterData> {
        return buildList {
            add(RegisterData.Text(Res.string.register_nick_name, Icons.Default.SmartToy, 12, nickName, onNickNameChange))
            add(RegisterData.Text(Res.string.register_name, Icons.Default.Face, 25, name, onNameChange))
            add(RegisterData.Text(Res.string.register_father_last_name, Icons.Default.Face, 25, fatherLastName, onFatherLastNameChange))
            add(RegisterData.Text(Res.string.register_mother_last_name, Icons.Default.Face, 25, motherLastName, onMotherLastNameChange))
            add(RegisterData.Date(Res.string.register_birth_date, Icons.Default.Event, 10, birthDate, onBirthDateChange))
            add(RegisterData.Text(Res.string.register_email, Icons.Default.AlternateEmail, 50, email, onEmailChange))
            add(RegisterData.Phone(Res.string.register_phone_number, Icons.Default.Smartphone, 20, phoneNumber, onPhoneChange))
            if (password != null && onPasswordChange != null) {
                add(RegisterData.Auth(Res.string.register_password, Icons.Default.Lock, 8, password, onPasswordChange))
            }
        }
    }
}