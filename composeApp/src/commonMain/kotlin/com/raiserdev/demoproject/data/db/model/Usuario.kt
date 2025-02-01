package com.raiserdev.demoproject.data.db.model

data class Usuario(
    val id: Long,
    var username: String,
    var email: String,
    var password: String,
    var nickname: String?,
    var numeroTelefonico: String?,
    var fechaCreacion: String,
    var fechaActualizacion: String
)