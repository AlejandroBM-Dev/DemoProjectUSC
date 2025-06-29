package com.raiserdev.demoproject.data.db.model

data class Usuario(
    val id: Long,
    val userName: String?,
    val userFathersName: String?,
    val userMothersName: String?,
    val birthDate: String?,
    val email: String?,
    val password: String?,
    val nickname: String?,
    val numeroTelefonico: String?,
    val fechaCreacion: String?,
    val fechaActualizacion: String?,
    val recordarUsuario: Boolean? = false,
)