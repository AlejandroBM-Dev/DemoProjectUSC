package com.raiserdev.demoproject.data.db.repository

import com.raiserdev.demoproject.Notas
import com.raiserdev.demoproject.data.db.model.Nota
import com.raiserdev.demoproject.domain.NotasRepository
import com.raiserdev.demoproject.notas.NotasProjectDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class NotaRepoImpl(
    private val database: NotasProjectDatabase
) : NotasRepository {

    private val queries get() = database.notasQueries

    override suspend fun getNotasByUsuario(usuarioId: Long): List<Nota> = withContext(Dispatchers.IO) {
        queries.getNotasByUsuario(usuarioId).executeAsList().map { it.toDomain() }
    }

    override suspend fun getNotaById(id: Long): Nota? = withContext(Dispatchers.IO) {
        queries.getNotaById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun insertNota(nota: Nota): Long = withContext(Dispatchers.IO) {
        queries.insertNota(
            titulo = nota.titulo,
            contenido = nota.contenido,
            usuario_id = nota.usuarioId,
            label_id = nota.labelId.toLong(),
        )
        queries.getNotas().executeAsList().last().id // obtén el último insertado
    }

    override suspend fun updateNota(nota: Nota): Boolean = withContext(Dispatchers.IO) {
        try {
            queries.updateNota(
                id = nota.id,
                titulo = nota.titulo,
                contenido = nota.contenido,
                label_id = nota.labelId.toLong(),
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteNota(id: Long): Boolean = withContext(Dispatchers.IO) {
        try {
            queries.deleteNota(id)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun Notas.toDomain(): Nota = Nota(
        id = id,
        titulo = titulo,
        contenido = contenido,
        usuarioId = usuario_id,
        labelId = label_id?.toInt() ?: 0,
        fechaCreacion = fecha_creacion.toString(),
        fechaActualizacion = fecha_actualizacion.toString()
    )
}