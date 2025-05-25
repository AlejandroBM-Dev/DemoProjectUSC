package com.raiserdev.demoproject.domain

import com.raiserdev.demoproject.data.db.model.Nota

interface NotaRepository {
    // Operaciones básicas CRUD
    suspend fun insertNota(nota: Nota): Long
    suspend fun updateNota(nota: Nota): Int
    suspend fun deleteNota(idNota: Int): Int
    suspend fun getNotaById(idNota: Int): Nota?

    // Operaciones específicas
    suspend fun getAllNotasByUsuario(idUsuario: Int): List<Nota>
    suspend fun getNotasPendientes(idUsuario: Int): List<Nota>
    suspend fun getNotasTerminadas(idUsuario: Int): List<Nota>
    suspend fun getNotasConRecordatorioProximo(idUsuario: Int): List<Nota>
    suspend fun searchNotasByTitle(idUsuario: Int, query: String): List<Nota>

    // Operaciones con filtros combinados
    suspend fun getNotasByTipo(idUsuario: Int, idTipo: Int?): List<Nota>
    suspend fun getNotasByFecha(idUsuario: Int, fecha: String): List<Nota>
    suspend fun getNotasByEstadoYFecha(idUsuario: Int, estado: Nota.EstadoNota, fechaInicio: String, fechaFin: String): List<Nota>
}