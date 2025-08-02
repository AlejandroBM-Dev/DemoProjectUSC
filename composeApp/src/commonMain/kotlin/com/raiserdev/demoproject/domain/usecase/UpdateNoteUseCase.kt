package com.raiserdev.demoproject.domain.usecase

import com.raiserdev.demoproject.data.db.model.Nota
import com.raiserdev.demoproject.domain.LabelRepository
import com.raiserdev.demoproject.domain.NotasRepository

class UpdateNoteUseCase(
    private val noteRepository: NotasRepository,
    private val labelRepository: LabelRepository
) {
    suspend operator fun invoke(nota: Nota) {
        val existingNote = noteRepository.getNotaById(nota.id)

        // Si cambió el label, ajustamos los contadores
        if (existingNote?.labelId != nota.labelId) {
            existingNote?.labelId?.let { labelRepository.decrementLabelCount(it.toLong()) }
            nota.labelId?.let { labelRepository.incrementLabelCount(it.toLong()) }
        }

        noteRepository.updateNota(nota)
    }
}