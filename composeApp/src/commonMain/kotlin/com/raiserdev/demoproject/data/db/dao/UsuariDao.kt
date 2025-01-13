package com.raiserdev.demoproject.data.db.dao

import com.raiserdev.demoproject.ProjectDatabase
import com.raiserdev.demoproject.Usuarios
import com.raiserdev.demoproject.data.db.model.Usuario

fun ProjectDatabase.setUsuario(usuario: Usuario) {
    return userQueries.insertUsuario(
        id = usuario.id,
        username = usuario.username,
        email = usuario.email,
        password = usuario.password,
        nickname = usuario.nickname,
        numero_telefonico = usuario.numeroTelefonico
    )
}

fun ProjectDatabase.getUsuarios(): List<Usuario> {
    val listUsuarios: List<Usuarios> = userQueries.getUsuarios().executeAsList()
    // Mapea cada objeto 'Usuarios' a tu data class 'Usuario'
    return listUsuarios.map { user ->
        Usuario(
            id = user.id,
            username = user.username,
            email = user.email,
            password = user.password,
            nickname = user.nickname,
            numeroTelefonico = user.numero_telefonico,
            fechaCreacion = user.fecha_creacion ?: "ERROR EN FECHA DE CREACIÓN",
            fechaActualizacion = user.fecha_actualizacion ?: "ERROR EN FECHA DE ACTUALIZACIÓN"
        )
    }
}

fun ProjectDatabase.getUsuarioById(id: Long): Usuario? {
    val usuarioGenerado: Usuarios? = userQueries.getUsuarioById(id).executeAsOneOrNull()
    return usuarioGenerado?.let {
        Usuario(
            id = it.id,
            username = it.username,
            email = it.email,
            password = it.password,
            nickname = it.nickname,
            numeroTelefonico = it.numero_telefonico,
            fechaCreacion = it.fecha_creacion ?: "ERROR EN FECHA DE CREACIÓN",
            fechaActualizacion = it.fecha_actualizacion ?: "ERROR EN FECHA DE ACTUALIZACIÓN"
        )
    }
}
