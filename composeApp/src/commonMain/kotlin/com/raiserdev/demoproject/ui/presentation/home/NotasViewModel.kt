package com.raiserdev.demoproject.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.data.db.model.Nota
import com.raiserdev.demoproject.data.ds.UserPreferencesRepository
import com.raiserdev.demoproject.domain.NotasRepository
import com.raiserdev.demoproject.utils.NEW_NOTE_ID
import com.raiserdev.demoproject.utils.showToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NotasViewModel(
    private val noteRepository: NotasRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    var mNoteId: Long = NEW_NOTE_ID

    private val _titleNote = MutableStateFlow("")
    val titleNote: StateFlow<String> get() = _titleNote

    private val _contentNote = MutableStateFlow("")
    val contentNote: StateFlow<String> get() = _contentNote.asStateFlow()

    fun onTitleNoteChange(newTitle: String) {
        _titleNote.value = newTitle
    }

    fun onContentNoteChange(newContent: String) {
        _contentNote.value = newContent
    }

    fun getNoteData(noteId: Long) {
        viewModelScope.launch {
            mNoteId = noteId
            val note = noteRepository.getNotaById(noteId)
            note?.let {
                _titleNote.value = it.titulo
                _contentNote.value = it.contenido ?: ""
            }
        }
    }
    fun saveNote(onSucces: (Boolean) -> Unit) {
        if (titleNote.value.isNotEmpty() && contentNote.value.isNotEmpty()) {
            viewModelScope.launch {
                val userPF = userPreferencesRepository.userPrefData.first()

                val newNote = Nota(
                    id = 0,
                    titulo = titleNote.value,
                    contenido = contentNote.value,
                    usuarioId = userPF.userId,
                    fechaCreacion = "",
                    fechaActualizacion = ""
                )

                if (mNoteId != NEW_NOTE_ID) {
                    updateFlow(
                        nota = newNote.copy(id = mNoteId),
                        onSucces
                    )
                } else  {
                    insertFlow(
                        nota = newNote,
                        onSucces
                    )
                }
            }
        } else {
            showToast("Completa todos los campos.")
            onSucces.invoke(false)
        }
    }

    private fun insertFlow(nota: Nota, onSucces: (Boolean) -> Unit){
        viewModelScope.launch {
            val insertNote = runCatching {
                noteRepository.insertNota(nota)
            }.getOrElse {
                showToast("Error al guardar la nota: ${it.message}")
                return@getOrElse 0
            }
            if (insertNote > 0) {
                showToast("Nota guardada con éxito.")
                _titleNote.value = ""
                _contentNote.value = ""
                onSucces.invoke(true)
            } else {
                showToast("No se pudo guardar la nota.")
                onSucces.invoke(false)
            }
        }

    }

    private fun updateFlow(nota: Nota, onSucces: (Boolean) -> Unit){
        viewModelScope.launch {
            val updateNote = runCatching {
                noteRepository.updateNota(nota)
            }.getOrElse {
                showToast("Error al actualizar la nota: ${it.message}")
                return@getOrElse false
            }
            if (updateNote) {
                showToast("Nota actualizada con éxito.")
                _titleNote.value = ""
                _contentNote.value = ""
                onSucces.invoke(true)
            } else {
                showToast("No se pudo actualizar la nota.")
                onSucces.invoke(false)
            }

        }
    }


}