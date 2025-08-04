package com.raiserdev.demoproject.ui.presentation.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.data.db.model.LabelsData
import com.raiserdev.demoproject.data.db.model.Nota
import com.raiserdev.demoproject.data.db.model.toDbEntity
import com.raiserdev.demoproject.data.ds.UserPreferencesRepository
import com.raiserdev.demoproject.domain.LabelRepository
import com.raiserdev.demoproject.domain.NotasRepository
import com.raiserdev.demoproject.utils.EMPTY_LIST_LABEL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val noteRepository: NotasRepository,
    private val labelRepository: LabelRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _labelsList = MutableStateFlow(mutableListOf<LabelsData>())
    val labelsList: StateFlow<MutableList<LabelsData>> get() = _labelsList.asStateFlow()

    private val _updateGridOrList = MutableStateFlow(false)
    val updateGridOrList: StateFlow<Boolean> get() = _updateGridOrList.asStateFlow()
    private val _listNotes = MutableStateFlow<List<Nota>>(emptyList())
    val listNotes: StateFlow<List<Nota>> get() = _listNotes
    private val _showDeleteDialog = MutableStateFlow(Pair(false, 0))
    val showDeleteDialog: StateFlow<Pair<Boolean, Int>> get() = _showDeleteDialog.asStateFlow()
    private val _showCloseSessionDialog = MutableStateFlow(false)
    val showCloseSessionDialog: StateFlow<Boolean> get() = _showCloseSessionDialog.asStateFlow()

    private val _pendingNoteToDeleteId = MutableStateFlow<Int?>(null)
    val pendingNoteToDeleteId: StateFlow<Int?> = _pendingNoteToDeleteId

    init {
        println("Nota.. homeViewModel")
        viewModelScope.launch {
            userPreferencesRepository.userPrefData.first().userId.let {
                val countListLabel = labelRepository.getAllById(it).size
                if (countListLabel == EMPTY_LIST_LABEL) {
                     labelRepository.insert(
                            LabelsData(
                                usuarioId = it,
                                label = "Personal",
                                color = Color.Blue
                            ).toDbEntity()
                        )
                     labelRepository.insert(
                            LabelsData(
                                usuarioId = it,
                                label = "Casa",
                                color = Color.Green
                            ).toDbEntity()
                        )
                     labelRepository.insert(
                            LabelsData(
                                usuarioId = it,
                                label = "Trabajo",
                                color = Color.Magenta
                            ).toDbEntity()
                        )
                }
                getLabels()
            }
        }
    }

    fun getLabels() {
        viewModelScope.launch {
            userPreferencesRepository
                .userPrefData
                .first()
                .userId
                .let { id ->
                    val labels = labelRepository.getAllById(id)
                    _labelsList.value = labels.toMutableList()
                }
        }
    }

    fun clearUserPreferences(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = userPreferencesRepository.clearData()
            onResult(result)
        }
    }

    fun showAllNotes() {
        viewModelScope.launch {
            val userPF = userPreferencesRepository.userPrefData.first()
            val notes = noteRepository.getNotasByUsuario(userPF.userId)
            _listNotes.value = notes.toMutableList()
        }
    }

    fun onDeleteNote(idNote: Int) {
        viewModelScope.launch {
            noteRepository.deleteNota(idNote.toLong())
        }
    }

    fun showDeleteDialog( show: Boolean, idNote: Int = 0 ) {
        _showDeleteDialog.value = Pair(show, idNote)
    }

    fun showCloseDialog( show: Boolean ) {
        _showCloseSessionDialog.value = show
    }
    fun getStatsChangeGridOrList() {
        var changeGridOrList = false
        viewModelScope.launch {
            changeGridOrList = userPreferencesRepository.sessionPrefData.first().changeGridOrList
            _updateGridOrList.value = changeGridOrList
        }
    }
    fun changeGridOrList(update: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateChangeGridOrList(update)
        }
        _updateGridOrList.value = update
    }

    fun getFilterByLabelId(labelId: Long) {
        viewModelScope.launch {
            val userId = userPreferencesRepository.userPrefData.first().userId
            val notes = noteRepository
                .getNotasByLabelId(
                    userId = userId,
                    labelId = labelId
                )
            _listNotes.value = notes.toMutableList()
        }
    }

    fun triggerAnimatedDelete(noteId: Int) {
        _pendingNoteToDeleteId.value = noteId
    }

    fun clearPendingDelete() {
        _pendingNoteToDeleteId.value = null
    }
}