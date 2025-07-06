package com.raiserdev.demoproject.data.domain.data

import androidx.compose.ui.graphics.vector.ImageVector
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
import org.jetbrains.compose.resources.StringResource


sealed class RegisterData {
    // Common fields
    abstract val title: StringResource
    abstract val icon: ImageVector
    abstract val type: DataType
    abstract val length: Int
    abstract val onValueChanged: (String) -> Unit

    // Numeric data
    data class Numeric(
        override val title: StringResource,
        override val icon: ImageVector,
        override val length: Int,
        val currentValue: StateFlow<Int>,
        override val onValueChanged: (String) -> Unit
    ) : RegisterData() {
        override val type: DataType = DataType.NUMERIC
    }

    data class Phone(
        override val title: StringResource,
        override val icon: ImageVector,
        override val length: Int,
        val fieldState: StateFlow<FieldState>,
        override val onValueChanged: (String) -> Unit
    ) : RegisterData() {
        override val type: DataType = DataType.STRING
    }

    // Hexadecimal data
    data class Hex(
        override val title: StringResource,
        override val icon: ImageVector,
        override val length: Int,
        val currentValue: StateFlow<String>,
        override val onValueChanged: (String) -> Unit
    ) : RegisterData() {
        override val type: DataType = DataType.HEXADECIMAL
    }

    // String data
    data class Text(
        override val title: StringResource,
        override val icon: ImageVector,
        override val length: Int,
        val fieldState: StateFlow<FieldState>,
        override val onValueChanged: (String) -> Unit
    ) : RegisterData() {
        override val type: DataType = DataType.STRING
    }

    // String data
    data class Auth(
        override val title: StringResource,
        override val icon: ImageVector,
        override val length: Int,
        val currentValue: StateFlow<Pair<String,String>> ,
        override val onValueChanged: (String) -> Unit,
    ) : RegisterData() {
        override val type: DataType = DataType.STRING
    }

    // Date data
    data class Date(
        override val title: StringResource,
        override val icon: ImageVector,
        override val length: Int,
        val fieldState: StateFlow<FieldState>,
        override val onValueChanged: (String) -> Unit
    ) : RegisterData() {
        override val type: DataType = DataType.DATE

        /**
         * Regex for validating dates in the format DD/MM/YYYY:
         * - Day: 01 to 31
         * - Month: 01 to 12
         * - Year: 4 digits
         */
        private val dateRegex = Regex("^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/(\\d{4})\$")

        fun isValidDate(dateStr: String): Boolean {
            return dateRegex.matches(dateStr)
        }
    }
}