package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.raiserdev.demoproject.utils.showToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotasViewModel: ViewModel() {

    private val _titleNote = MutableStateFlow("")
    val titleNote: StateFlow<String> get() = _titleNote

    private val _contentNote = MutableStateFlow("")
    val contentNote: StateFlow<String> get() = _contentNote.asStateFlow()

    fun onTitleNoteChange(newTitle: String) {
        _titleNote.value = newTitle
        println("titleNote: $newTitle _ titleNote: ${titleNote.value} ")
    }

    fun onContentNoteChange(newContent: String) {
        _contentNote.value = newContent
    }

    fun saveNote() {
        if (titleNote.value.isNotEmpty() && contentNote.value.isNotEmpty()) {
            showToast("Nota guardada con éxito.")
        } else {
            showToast("Completa todos los campos.")
        }
    }

}