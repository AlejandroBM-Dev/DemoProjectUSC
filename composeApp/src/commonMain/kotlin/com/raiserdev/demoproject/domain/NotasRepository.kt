package com.raiserdev.demoproject.domain

import com.raiserdev.demoproject.data.db.model.Usuario

interface NotasRepository {
    suspend fun addUser(usuario: Usuario)
    suspend fun getUserById(id: Long): Usuario?
    suspend fun updateUser(usuario: Usuario)
    suspend fun deleteUser(id:Long)

    suspend fun login(email: String, password: String): Usuario?
}