package com.raiserdev.demoproject.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.domain.UserRepository
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class HomeViewModel(
    private val repository: UserRepository
):ViewModel() {

    fun closeApp() {
        viewModelScope.launch {
            repository.clearRecordarUsuario()
        }
    }
}