package com.raiserdev.demoproject.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raiserdev.demoproject.data.ds.UserPreferencesRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    fun clearUserPreferences(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = userPreferencesRepository.clearData()
            onResult(result)
        }
    }
}