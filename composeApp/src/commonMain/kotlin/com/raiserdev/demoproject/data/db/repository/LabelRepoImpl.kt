package com.raiserdev.demoproject.data.db.repository

import androidx.compose.ui.graphics.toArgb
import com.raiserdev.demoproject.Labels
import com.raiserdev.demoproject.data.db.model.Label
import com.raiserdev.demoproject.data.db.model.LabelsData
import com.raiserdev.demoproject.domain.LabelRepository
import com.raiserdev.demoproject.notas.NotasProjectDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class LabelRepoImpl(
    database: NotasProjectDatabase
): LabelRepository {
    private val labelQueries = database.labelsQueries
    override suspend fun insert(label: Label): Long = withContext(Dispatchers.IO ) {
        labelQueries.insertLabel(
            label = label.label,
            usuario_id = label.usuarioId,
            count = label.count.toLong(),
            colorHex = label.colorHex
        )
        labelQueries.getLabels().executeAsList().last().id
    }

    override suspend fun getAllById(userId: Long): List<LabelsData> = withContext(Dispatchers.IO) {
        labelQueries.getUserLabelsById(userId).executeAsList()?.map { it.toDomain() } as List<LabelsData>
    }

    override suspend fun getById(id: Long): LabelsData? = withContext(Dispatchers.IO) {
        labelQueries.getLabelById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun update(label: LabelsData): Boolean = withContext(Dispatchers.IO) {
        try {
            labelQueries.updateLabel(
                id = label.idLabel.toLong(),
                label = label.label,
                count = label.count.toLong(),
                colorHex = label.color.toArgb().toLong()
            )
            true
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    override suspend fun delete(id: Long): Boolean = withContext(Dispatchers.IO) {
        try {
            labelQueries.deleteLabel(id)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun Labels.toDomain(): LabelsData = Label(
        id = id.toInt(),
        usuarioId = usuario_id,
        label = label,
        count = count?.toInt() ?: 0,
        colorHex = colorHex
    ).toDomain()
}