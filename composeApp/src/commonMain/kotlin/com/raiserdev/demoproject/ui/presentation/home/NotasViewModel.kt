package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.data.db.model.LabelsData
import com.raiserdev.demoproject.data.db.model.Nota
import com.raiserdev.demoproject.data.ds.UserPreferencesRepository
import com.raiserdev.demoproject.domain.LabelRepository
import com.raiserdev.demoproject.domain.NotasRepository
import com.raiserdev.demoproject.utils.NEW_NOTE_ID
import com.raiserdev.demoproject.utils.showToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.collections.mutableListOf

class NotasViewModel(
    private val noteRepository: NotasRepository,
    private val labelRepository: LabelRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    /*init {
        getLabels()
    }*/
    var mNoteId: Long = NEW_NOTE_ID
    private var userId: Long = 0
    private val _labels = MutableStateFlow(mutableListOf<LabelsData>())
    val labels: StateFlow<MutableList<LabelsData>> get() = _labels.asStateFlow()
    private val _idLabel = MutableStateFlow(0)
    val idLabel: StateFlow<Int> get() = _idLabel.asStateFlow()

    private val _colorLabel = MutableStateFlow(Color.LightGray)
    val colorLabel: StateFlow<Color> get() = _colorLabel.asStateFlow()

    private val _titleNote = MutableStateFlow("")
    val titleNote: StateFlow<String> get() = _titleNote
    private val _contentNote = MutableStateFlow("")
    val contentNote: StateFlow<String> get() = _contentNote.asStateFlow()

    fun loadData(noteId: Long) {
        println("loadData _noteId: $noteId")
        viewModelScope.launch {
            userId = userPreferencesRepository.userPrefData.first().userId
        }.invokeOnCompletion {
            getLabels {
                if (noteId != NEW_NOTE_ID) {
                    getNoteData(noteId)
                } else {
                    //Solo asignamos la lebel general...
                    setIdLabelChange( validateAssignLabel(NEW_NOTE_ID.toInt()) )
                    //_colorLabel.value =
                }
            }
        }
    }

    fun setTitleNoteChange(newTitle: String) {
        _titleNote.value = newTitle
    }

    fun setContentNoteChange(newContent: String) {
        _contentNote.value = newContent
    }

    fun setIdLabelChange(newIdLabel: Int) {
        newIdLabel.let {
            _idLabel.value = it
            getColorLabel(it.toLong())
        }
    }

    fun getNoteData(noteId: Long) {
        viewModelScope.launch {
            mNoteId = noteId
            val note = noteRepository.getNotaById(noteId)
            note?.let {
                setTitleNoteChange(it.titulo)
                setContentNoteChange(it.contenido ?: "")
                setIdLabelChange(it.labelId)
            }
            //val label = labelRepository.getLabelById(note.labelId)
        }
    }

    private fun validateAssignLabel(idLabel: Int): Int {
        val existLabel = _labels.value.find { it.idLabel == idLabel }
        val foundIdLabel = if (_labels.value.size <= 1 && idLabel == 0) {
            _labels.value.first().idLabel
        } else if (existLabel != null){
            idLabel
        } else {
            _labels.value.find { it.label == "Mostrar todo" }?.idLabel
        }
        getColorLabel(foundIdLabel?.toLong() ?: throw IllegalArgumentException("Error al no encontrar ID de la etiqueta."))

        return foundIdLabel ?: throw IllegalArgumentException("Error al no encontrar ID de la etiqueta.")
    }
    fun getLabels(onSucces: (Boolean) -> Unit) {
        viewModelScope.launch {
            val userPref = userPreferencesRepository.userPrefData.first()
            val labels = labelRepository.getAllById(userPref.userId)
            _labels.value = labels.toMutableList()
            onSucces.invoke(true)
        }
    }

    private fun getColorLabel(idLabel: Long) {
        viewModelScope.launch {
            val labelData = labelRepository.getById(idLabel)
            _colorLabel.value = labelData?.color ?: Color.LightGray
        }
    }

    fun saveNote(onSucces: (Boolean) -> Unit) {
        if (titleNote.value.isNotEmpty() && contentNote.value.isNotEmpty()) {
            viewModelScope.launch {

                val newNote = Nota(
                    id = 0,
                    titulo = titleNote.value,
                    contenido = contentNote.value,
                    usuarioId = userId,
                    labelId = idLabel.value,
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
            //Usa un LONG para saber si se pudo guardar la nota
            val isSuccess = if (insertNote > 0) {
                cleanData("Nota guardada con éxito.")
                true
            } else {
                showToast("No se pudo guardar la nota.")
                false
            }
            onSucces.invoke(
                isSuccess
            )
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
            //Usa un BOOLEAN para saber si se pudo actualizar la nota
            val isSuccess = if (updateNote) {
                cleanData("Nota actualizada con éxito.")
                true
            } else {
                showToast("No se pudo actualizar la nota.")
                false
            }

            onSucces.invoke(
                isSuccess
            )
        }
    }

    private fun cleanData(
        message: String,
    ) {
        showToast(message)
        _titleNote.value = ""
        _contentNote.value = ""
    }


}