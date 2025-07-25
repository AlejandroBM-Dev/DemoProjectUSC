package com.raiserdev.demoproject.data.db.model

import androidx.compose.material3.Label
import androidx.compose.ui.graphics.Color

data class Label(
    val id: Int,
    val usuarioId: Long,
    val label: String,
    val count: Int,
    val colorHex: Long
) {
    //Para el uso interno
    fun toDomain() = LabelsData(
        idLabel = id,
        usuarioId = usuarioId,
        label = label,
        count = count,
        color = Color(colorHex)
    )
}
//Para la base de datos...
fun LabelsData.toDbEntity() = Label(
    id = idLabel,
    usuarioId = usuarioId,
    label = label,
    count = count,
    colorHex = color.value.toLong()
)

data class LabelsData(
    val idLabel: Int,
    val usuarioId: Long,
    val label: String,
    val count: Int,
    val color: Color
)