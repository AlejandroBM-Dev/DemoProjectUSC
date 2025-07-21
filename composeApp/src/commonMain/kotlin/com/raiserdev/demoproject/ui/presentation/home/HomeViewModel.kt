package com.raiserdev.demoproject.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.data.db.model.Nota
import com.raiserdev.demoproject.data.ds.UserPreferencesRepository
import com.raiserdev.demoproject.domain.NotasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val noteRepository: NotasRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _updateGridOrList = MutableStateFlow(false)
    val updateGridOrList: StateFlow<Boolean> get() = _updateGridOrList.asStateFlow()
    private val _listNotes = MutableStateFlow(mutableListOf<Nota>())
    val listNotes: StateFlow<MutableList<Nota>> get() = _listNotes.asStateFlow()

    private val _showCloseSessionDialog = MutableStateFlow(false)
    val showCloseSessionDialog: StateFlow<Boolean> get() = _showCloseSessionDialog.asStateFlow()

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