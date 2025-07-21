package com.raiserdev.demoproject.data.ds

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.raiserdev.demoproject.data.ds.model.SessionPrefData
import com.raiserdev.demoproject.data.ds.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: PrefsDataStore
) {
    private object PreferencesKeys {
        //User
        val USER_ID = longPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        //Session
        val CHANGE_GRID_OR_LIST = booleanPreferencesKey("change_grid_or_list")
    }

    val userPrefData: Flow<UserData> = dataStore.data.map { preferences ->
        UserData(
            userId = preferences[PreferencesKeys.USER_ID] ?: 0L,
            userName = preferences[PreferencesKeys.USER_NAME] ?: "",
            userEmail = preferences[PreferencesKeys.USER_EMAIL] ?: "",
            isLoggedIn = preferences[PreferencesKeys.IS_LOGGED_IN] ?: false,
        )
    }

    val sessionPrefData: Flow<SessionPrefData> = dataStore.data.map { preferences ->
        SessionPrefData(
            changeGridOrList = preferences[PreferencesKeys.CHANGE_GRID_OR_LIST] ?: false,
        )
    }


    suspend fun updateUserId(userId: Long) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[PreferencesKeys.USER_ID] = userId
            }
        }
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

    suspend fun updateChangeGridOrList(update: Boolean) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[PreferencesKeys.CHANGE_GRID_OR_LIST] = update
            }
        }
    }
    suspend fun clearData(): Boolean {
        return runCatching {
            dataStore.updateData { preferences ->
                preferences.toMutablePreferences().apply {
                    //User
                    remove(PreferencesKeys.USER_ID)
                    remove(PreferencesKeys.USER_NAME)
                    remove(PreferencesKeys.USER_EMAIL)
                    remove(PreferencesKeys.IS_LOGGED_IN)
                    //Session
                    remove(PreferencesKeys.CHANGE_GRID_OR_LIST)
                }
            }
        }.isSuccess
    }
}