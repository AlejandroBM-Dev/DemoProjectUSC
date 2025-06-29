package com.raiserdev.demoproject.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.raiserdev.demoproject.notas.NotasProjectDatabase

actual class DriverFactory(private val appContext: Context) {
    actual fun createDriver(): SqlDriver {
        // Asegúrate de que appContext esté inicializado
        return AndroidSqliteDriver(NotasProjectDatabase.Schema, appContext, "NotasProjectDatabase.db")
    }
}