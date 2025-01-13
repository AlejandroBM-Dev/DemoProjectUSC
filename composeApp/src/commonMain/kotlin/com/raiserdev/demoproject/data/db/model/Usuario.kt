package com.raiserdev.demoproject.data.db.model

data class Usuario(
    val id: Long,
    val username: String,
    val email: String,
    val password: String,
    val nickname: String?,
    val numeroTelefonico: String?,
    val fechaCreacion: String,
    val fechaActualizacion: String
)