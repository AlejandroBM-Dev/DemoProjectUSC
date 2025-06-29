package com.raiserdev.demoproject.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.raiserdev.demoproject.notas.NotasProjectDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = NotasProjectDatabase.Schema,
            name = "NotasProjectDatabase.db"
        )
    }
}