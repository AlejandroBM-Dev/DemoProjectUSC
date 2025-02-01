package com.raiserdev.demoproject.data.db.dao

import com.raiserdev.demoproject.ProjectDatabase
import com.raiserdev.demoproject.Usuarios
import com.raiserdev.demoproject.data.db.model.Usuario

fun ProjectDatabase.setUsuario(usuario: Usuario): Boolean {
    return try {
        userQueries.insertUsuario(
            id = usuario.id,
            username = usuario.userName ?: throw IllegalArgumentException("El nombre de usuario no puede ser nulo"),
            user_fathers_name = usuario.userFathersName ?: throw IllegalArgumentException("El nombre del padre no puede ser nulo"),
            user_mothers_name = usuario.userMothersName ?: throw IllegalArgumentException("El nombre de la madre no puede ser nulo"),
            birth_date = usuario.birthDate ?: throw IllegalArgumentException("La fecha de nacimiento no puede ser nula"),
            email = usuario.email ?: throw IllegalArgumentException("El email no puede ser nulo"),
            password = usuario.password ?: throw IllegalArgumentException("La contraseña no puede ser nula"),
            nickname = usuario.nickname,
            numero_telefonico = usuario.numeroTelefonico
        )

        // Validar si el usuario fue insertado correctamente
        val usuarioInsertado = getUsuarioById(usuario.id)
        usuarioInsertado != null
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun ProjectDatabase.getUsuarios(): Pair<Boolean, List<Usuario>> {
    return try {
        val listUsuarios: List<Usuarios> = userQueries.getUsuarios().executeAsList()
        val mappedUsuarios = listUsuarios.map { user ->
            Usuario(
                id = user.id,
                userName = user.username,
                userFathersName = user.user_fathers_name,
                userMothersName = user.user_mothers_name,
                birthDate = user.birth_date,
                email = user.email,
                password = user.password,
                nickname = user.nickname,
                numeroTelefonico = user.numero_telefonico,
                fechaCreacion = user.fecha_creacion ?: throw IllegalArgumentException("La fecha de creación no puede ser nula"),
                fechaActualizacion = user.fecha_actualizacion ?: throw IllegalArgumentException("La fecha de actualización no puede ser nula")
            )
        }
        Pair(mappedUsuarios.isNotEmpty(), mappedUsuarios) // Devuelve true si hay usuarios
    } catch (e: Exception) {
        e.printStackTrace()
        Pair(false, emptyList()) // Devuelve false en caso de error
    }
}

fun ProjectDatabase.getUsuarioById(id: Long): Pair<Boolean, Usuario?> {
    return try {
        val usuarioGenerado: Usuarios? = userQueries.getUsuarioById(id).executeAsOneOrNull()
        val usuario = usuarioGenerado?.let {
            Usuario(
                id = it.id,
                userName = it.username,
                userFathersName = it.user_fathers_name,
                userMothersName = it.user_mothers_name,
                birthDate = it.birth_date,
                email = it.email,
                password = it.password,
                nickname = it.nickname,
                numeroTelefonico = it.numero_telefonico,
                fechaCreacion = it.fecha_creacion ?: "ERROR EN FECHA DE CREACIÓN",
                fechaActualizacion = it.fecha_actualizacion ?: "ERROR EN FECHA DE ACTUALIZACIÓN"
            )
        }
        Pair(usuario != null, usuario) // Devuelve true si encontró el usuario
    } catch (e: Exception) {
        e.printStackTrace()
        Pair(false, null) // Devuelve false si hubo un error
    }
}
