package com.raiserdev.demoproject.data.ds

import com.raiserdev.demoproject.data.ds.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserPreferencesInterface {
    val userData: Flow<UserData>
    suspend fun updateUserName(userName: String)
    suspend fun updateUserEmail(userEmail: String)
    suspend fun updateIsLoggedIn(isLoggedIn: Boolean)
    suspend fun clearUserData()
}