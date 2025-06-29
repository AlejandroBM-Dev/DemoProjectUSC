package com.raiserdev.demoproject.domain

import com.raiserdev.demoproject.data.db.model.Nota

interface NotasRepository {
    suspend fun getNotasByUsuario(usuarioId: Long): List<Nota>
    suspend fun getNotaById(id: Long): Nota?
    suspend fun insertNota(nota: Nota): Long
    suspend fun updateNota(nota: Nota): Boolean
    suspend fun deleteNota(id: Long): Boolean
}