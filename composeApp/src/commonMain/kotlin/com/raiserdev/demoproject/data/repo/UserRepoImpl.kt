package com.raiserdev.demoproject.data.repo

import com.raiserdev.demoproject.ProjectDatabase
import com.raiserdev.demoproject.UserQueries
import com.raiserdev.demoproject.data.db.model.LoginResult
import com.raiserdev.demoproject.data.db.model.Usuario
import com.raiserdev.demoproject.domain.UserRepository

class UserRepoImpl(
    database: ProjectDatabase
): UserRepository {
    private val userQueries: UserQueries = database.userQueries

    override suspend fun addUser(usuario: Usuario) {

        userQueries.apply {
            transaction {
                try {
                    insertUsuario(
                        username = usuario.userName ?: throw IllegalArgumentException("Username cannot be null"),
                        user_fathers_name = usuario.userFathersName ?: throw IllegalArgumentException("User's fathers name cannot be null"),
                        user_mothers_name = usuario.userMothersName ?: throw IllegalArgumentException("User's mothers name cannot be null"),
                        birth_date = usuario.birthDate ?: throw IllegalArgumentException("Birth date cannot be null"),
                        email = usuario.email ?: throw IllegalArgumentException("Email cannot be null"),
                        password = usuario.password ?: throw IllegalArgumentException("Password cannot be null"),
                        nickname = usuario.nickname, // Ahora en el orden correcto
                        numero_telefonico = usuario.numeroTelefonico ?: throw IllegalArgumentException("Phone number cannot be null"),
                        // fecha_creacion y fecha_actualizacion no necesitan ser pasados, ya que usas CURRENT_TIMESTAMP
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
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

    override suspend fun existsByEmail(email: String): Boolean {
        return userQueries.transactionWithResult {
            userQueries.existsByEmail(email).executeAsOne() == EXIST_ONLY_ONE
        }
    }

    override suspend fun existsByTelefono(telefono: String): Boolean {
        return userQueries.transactionWithResult {
            userQueries.existsByTelefono(telefono).executeAsOne() == EXIST_ONLY_ONE
        }
    }

    override suspend fun validateUser(credential: String, password: String): LoginResult? {
        return userQueries.transactionWithResult {
            userQueries
                .validateUser(
                    credential = credential,
                    password = password
                )
                .executeAsOneOrNull()
                ?.let { result ->
                    LoginResult(
                        id = result.id,
                        userName = result.username,
                        nickname = result.nickname,
                        email = result.email,
                        numeroTelefonico = result.numero_telefonico
                    )
                }
        }
    }

    override suspend fun updateRecordarUsuario(id: Long) {
        userQueries.transaction {
            userQueries.updateRecordarUsuarioById(id)
        }
    }

    override suspend fun clearRecordarUsuario() {
        userQueries.transaction {
            userQueries.clearRecordarUsuario()
        }
    }

    override suspend fun getUsuarioRecordado(): Usuario? {
        val userExist = userQueries.getUsuarioRecordado().executeAsList()

        return if (userExist == null) {
            null
        } else {
            val user = userExist[0]
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
    companion object {
        private const val EXIST_ONLY_ONE = 1L
    }

}