package com.raiserdev.demoproject.data.db.model

data class Nota(
    val idNota: Int = 0,  // 0 para insertar nueva nota (autoincrement)
    val idUsuario: Int = 0,
    val idTipo: Int? = null,
    val titulo: String ?= null,
    val descripcion: String? = null,
    val fecha: String? = null,
    val ubicacion: String? = null,
    val media: String? = null,  // Puede ser una URI o path al archivo
    val recordatorio: String? = null,
    val estado: EstadoNota = EstadoNota.PENDIENTE,
    val fechaCreacion: String? = null,
    val fechaActualizacion: String? = null,
) {
    enum class EstadoNota {
        TERMINADA,
        PENDIENTE
    }
}