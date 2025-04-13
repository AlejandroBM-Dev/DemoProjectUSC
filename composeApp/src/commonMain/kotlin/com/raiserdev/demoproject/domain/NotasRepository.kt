package com.raiserdev.demoproject.domain

import app.cash.sqldelight.Query
import com.raiserdev.demoproject.data.db.model.LoginResult
import com.raiserdev.demoproject.data.db.model.Usuario

interface UserRepository {
    suspend fun addUser(usuario: Usuario)
    suspend fun getUserById(id: Long): Usuario?
    suspend fun updateUser(usuario: Usuario)
    suspend fun deleteUser(id:Long)

    suspend fun existsByEmail(email: String): Boolean
    suspend fun existsByTelefono(telefono: String): Boolean
    suspend fun validateUser(credential:String, password: String): LoginResult?

    suspend fun updateRecordarUsuario(id: Long)
    suspend fun clearRecordarUsuario()
}