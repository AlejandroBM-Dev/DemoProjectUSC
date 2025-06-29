package com.raiserdev.demoproject.data.db.repository

import com.raiserdev.demoproject.data.db.model.Nota
import com.raiserdev.demoproject.data.ds.UserPreferencesRepository
import com.raiserdev.demoproject.data.ds.model.UserData
import com.raiserdev.demoproject.domain.NotasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val fakeNotasRepo = object : NotasRepository {
    override suspend fun getNotasByUsuario(usuarioId: Long) = emptyList<Nota>()
    override suspend fun getNotaById(id: Long) = null
    override suspend fun insertNota(nota: Nota) = 1L
    override suspend fun updateNota(nota: Nota) = true
    override suspend fun deleteNota(id: Long) = true
}
