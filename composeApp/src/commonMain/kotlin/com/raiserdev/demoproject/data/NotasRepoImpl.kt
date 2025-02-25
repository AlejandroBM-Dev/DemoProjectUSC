package com.raiserdev.demoproject.data

import com.raiserdev.demoproject.ProjectDatabase
import com.raiserdev.demoproject.data.db.model.Usuario
import com.raiserdev.demoproject.domain.NotasRepository

class UserRepoImpl(
    database: ProjectDatabase,
): NotasRepository {
    private val userQueries = database.userQueries

    override suspend fun addUser(usuario: Usuario) {
        userQueries.transaction {
            userQueries.insertUsuario(
                nickname = usuario.nickname,
                username = usuario.userName ?: throw IllegalArgumentException("Username cannot be null"),
                user_fathers_name = usuario.userFathersName ?: throw IllegalArgumentException("User's fathers name cannot be null"),
                user_mothers_name = usuario.userMothersName ?: throw IllegalArgumentException("User's mothers name cannot be null"),
                birth_date = usuario.birthDate ?: throw IllegalArgumentException("Birth date cannot be null"),
                email = usuario.email ?: throw IllegalArgumentException("Email cannot be null"),
                password = usuario.password ?: throw IllegalArgumentException("Password cannot be null"),
                numero_telefonico = usuario.numeroTelefonico ?: throw IllegalArgumentException("Phone number cannot be null"),
            )
        }
    }

    override suspend fun getUserById(id: Long): Usuario? {
        val user = userQueries.getUsuarioById(id).executeAsOneOrNull()
        return if (user == null) {
             null
        } else {
            Usuario(
                id = user.id,
                nickname = user.nickname,
                userName = user.username,
                userFathersName = user.user_fathers_name,
                userMothersName = user.user_mothers_name,
                birthDate = user.birth_date,
                email = user.email,
                password = user.password,
                numeroTelefonico = user.numero_telefonico,
                fechaCreacion = user.fecha_creacion,
                fechaActualizacion = user.fecha_actualizacion
            )
        }

    }

    override suspend fun updateUser(usuario: Usuario) {
        userQueries.transaction {
            userQueries.updateUsuario(
                id = usuario.id,
                nickname = usuario.nickname,
                username = usuario.userName ?: throw IllegalArgumentException("Username cannot be null"),
                user_fathers_name = usuario.userFathersName ?: throw IllegalArgumentException("User's fathers name cannot be null"),
                user_mothers_name = usuario.userMothersName ?: throw IllegalArgumentException("User's mothers name cannot be null"),
                birth_date = usuario.birthDate ?: throw IllegalArgumentException("Birth date cannot be null"),
                email = usuario.email ?: throw IllegalArgumentException("Email cannot be null"),
                password = usuario.password ?: throw IllegalArgumentException("Password cannot be null"),
                numero_telefonico = usuario.numeroTelefonico ?: throw IllegalArgumentException("Phone number cannot be null"),
            )
        }
    }

    override suspend fun deleteUser(id: Long) {
        userQueries.transaction {
            userQueries.deleteUsuario(id)
        }
    }

    override suspend fun login(email: String, password: String): Usuario? {
        TODO("Not yet implemented")
    }
}