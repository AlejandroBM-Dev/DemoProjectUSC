package com.raiserdev.demoproject.data.ds

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.raiserdev.demoproject.data.ds.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: PrefsDataStore
) {
    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val userPrefData: Flow<UserData> = dataStore.data.map { preferences ->
        UserData(
            userName = preferences[PreferencesKeys.USER_NAME] ?: "",
            userEmail = preferences[PreferencesKeys.USER_EMAIL] ?: "",
            isLoggedIn = preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
        )
    }

    suspend fun updateUserName(userName: String) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[PreferencesKeys.USER_NAME] = userName
            }
        }
    }

    suspend fun updateEmail(email: String) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[PreferencesKeys.USER_EMAIL] = email
            }
        }
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[PreferencesKeys.IS_LOGGED_IN] = isLoggedIn
            }
        }
    }

    suspend fun clearData(): Boolean {
        return runCatching {
            dataStore.updateData { preferences ->
                preferences.toMutablePreferences().apply {
                    remove(PreferencesKeys.USER_NAME)
                    remove(PreferencesKeys.USER_EMAIL)
                    remove(PreferencesKeys.IS_LOGGED_IN)
                }
            }
        }.isSuccess
    }
}