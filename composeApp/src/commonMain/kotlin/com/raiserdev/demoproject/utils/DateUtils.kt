package com.raiserdev.demoproject.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
val now: Instant = Clock.System.now()


@OptIn(ExperimentalTime::class)
fun parseDdMmYyyy(dateString: String): String? {
    println("dateString: $dateString")

    // 1. Validar longitud (sin separadores => 8 caracteres)
    if (dateString.length != 8) return "Formato incorrecto"

    // 2. Extraer día, mes y año
    val day = dateString.substring(0, 2).toIntOrNull() ?: return "Día incorrecto"
    val month = dateString.substring(2, 4).toIntOrNull() ?: return "Mes incorrecto"
    val year = dateString.substring(4, 8).toIntOrNull() ?: return "Año incorrecto"
    println("day:$day _ month: $month _ year:$year")

    // 3. Revisar rangos básicos
    if (day !in 1..31) return "Dia incorrecto"
    if (month !in 1..12) return "Mes incorrecto"

    // 4. Construir LocalDate de kotlinx.datetime
    val date = try {
        LocalDate(year, month, day)
    } catch (e: IllegalArgumentException) {
        // Cae aquí si la combinación no existe (ej. 31/11/2023, 29/02 en año no bisiesto, etc.)
        return "Día incorrecto"
    }

    // 5. Comparar con la fecha actual (Clock.System.now())

    val currentDate = now.toLocalDateTime(TimeZone.currentSystemDefault())

    return if (date > currentDate.date) {
        "Formato mayor al date actual"
    } else {
        null
    }
}