package com.raiserdev.demoproject.domain

import com.raiserdev.demoproject.data.db.model.Label
import com.raiserdev.demoproject.data.db.model.LabelsData

interface LabelRepository {
    suspend fun insert(label: Label): Long
    suspend fun getAllById(id: Long): List<LabelsData>
    suspend fun getById(id: Long): LabelsData?
    suspend fun update(label: LabelsData): Boolean
    suspend fun delete(id: Long): Boolean
}