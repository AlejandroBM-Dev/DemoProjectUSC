package com.raiserdev.demoproject.data.ds.model

data class UserData(
    val userId: Long = 0L,
    val userName: String = "",
    val userEmail: String = "",
    val isLoggedIn: Boolean = false
)