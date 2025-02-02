package com.raiserdev.demoproject.ui.state

data class FieldState(
    val text: String = "",
    val isError: Boolean = false,
    val errorMessage: String? = null
)