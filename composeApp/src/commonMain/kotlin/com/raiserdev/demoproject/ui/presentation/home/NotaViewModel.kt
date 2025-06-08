package com.raiserdev.demoproject.ui.presentation.home

import androidx.collection.mutableIntSetOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.data.db.model.Nota
import com.raiserdev.demoproject.domain.NotaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotaViewModel(
    private val repository: NotaRepository
) : ViewModel() {

    private val _idNote = MutableStateFlow<Int>(0)
    val idNote: StateFlow<Int> get() = _idNote

    private val _notes = MutableStateFlow<Nota>(Nota())
    val notes: StateFlow<Nota> get() = _notes


    fun getNote(id: Int,onNewNote:() -> Unit, onSuccess: (Nota) -> Unit,onError: (String) -> Unit) {
        if (id == 0) {
            onNewNote.invoke()
        } else {
            viewModelScope.launch {
                val note = repository.getNotaById(id)
                if (note != null) {
                    onSuccess.invoke(note)
                } else {
                    onError.invoke("La nota no existe")
                }
            }
        }
    }


    fun isUpdated() {

    }

    fun inserNote(note: Nota) {
        /*val note = Nota()
        viewModelScope.launch {
            repository.insertNota()
        }*/
    }
}