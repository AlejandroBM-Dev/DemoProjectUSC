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
    private val _listNotes = MutableStateFlow(mutableListOf<Nota>())
    val listNotes: StateFlow<MutableList<Nota>> get() = _listNotes.asStateFlow()

    private val _showCloseSessionDialog = MutableStateFlow(false)
    val showCloseSessionDialog: StateFlow<Boolean> get() = _showCloseSessionDialog.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepository.userPrefData.first().userId.let {
                println("userId: $it")
                val countListLabel = labelRepository.getAllById(it).size
                println("countListLabel: $countListLabel")

                if(countListLabel == EMPTY_LIST_LABEL) {
                    println("insert label")
                    val insert =
                    labelRepository.insert(
                        LabelsData(
                            idLabel = 0,
                            usuarioId = it,
                            label = "Mostrar todo",
                            count = 0,
                            color = Color.Blue
                        ).toDbEntity()
                    )

                    println("insert: $insert")
                }
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
            println("nota: $notes")

            _listNotes.value = notes.toMutableList()
        }
    }

    fun showCloseDialog( show: Boolean ) {
        _showCloseSessionDialog.value = show
    }
    fun getStatsChangeGridOrList() {
        var changeGridOrList = false
        viewModelScope.launch {
            changeGridOrList = userPreferencesRepository.sessionPrefData.first().changeGridOrList
            println("last: $changeGridOrList")
            _updateGridOrList.value = changeGridOrList
        }
    }
    fun changeGridOrList(update: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateChangeGridOrList(update)
        }
        _updateGridOrList.value = update
    }
}