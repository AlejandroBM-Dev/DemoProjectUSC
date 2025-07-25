package com.raiserdev.demoproject.data.db.model

data class Nota(
    val id: Long,
    val titulo: String,
    val contenido: String?,
    val usuarioId: Long,
    val labelId: Int = 0,
    val fechaCreacion: String,
    val fechaActualizacion: String
)