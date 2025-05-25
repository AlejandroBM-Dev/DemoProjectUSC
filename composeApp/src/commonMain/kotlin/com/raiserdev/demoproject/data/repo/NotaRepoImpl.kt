package com.raiserdev.demoproject.data.repo

import com.raiserdev.demoproject.ProjectDatabase
import com.raiserdev.demoproject.data.db.model.Nota
import com.raiserdev.demoproject.domain.NotaRepository

class NotaRepoImpl(
    database: ProjectDatabase
): NotaRepository {
    private val noteQueries = database.notaQueries

    override suspend fun insertNota(nota: Nota): Long {
        return noteQueries.transactionWithResult {
            noteQueries.insertNota(
                id_usuario = nota.idUsuario.toLong(),
                id_tipo = nota.idTipo?.toLong(),
                titulo = nota.titulo,
                descripcion = nota.descripcion,
                fecha = nota.fecha ?: throw IllegalArgumentException("Fecha is required"),
                ubicacion = nota.ubicacion,
                media = nota.media,
                recordatorio = nota.recordatorio,
                estado = nota.estado.name
            )
            noteQueries.lastInsertRowId().executeAsOne()
        }

    }

    override suspend fun updateNota(nota: Nota): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNota(idNota: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getNotaById(idNota: Int): Nota? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllNotasByUsuario(idUsuario: Int): List<Nota> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotasPendientes(idUsuario: Int): List<Nota> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotasTerminadas(idUsuario: Int): List<Nota> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotasConRecordatorioProximo(idUsuario: Int): List<Nota> {
        TODO("Not yet implemented")
    }

    override suspend fun searchNotasByTitle(
        idUsuario: Int,
        query: String
    ): List<Nota> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotasByTipo(
        idUsuario: Int,
        idTipo: Int?
    ): List<Nota> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotasByFecha(
        idUsuario: Int,
        fecha: String
    ): List<Nota> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotasByEstadoYFecha(
        idUsuario: Int,
        estado: Nota.EstadoNota,
        fechaInicio: String,
        fechaFin: String
    ): List<Nota> {
        TODO("Not yet implemented")
    }
}