package com.raiserdev.demoproject.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.raiserdev.demoproject.data.db.createTriggers
import com.raiserdev.demoproject.notas.NotasProjectDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = NativeSqliteDriver(
            schema = NotasProjectDatabase.Schema,
            name = "NotasProjectDatabase.db"
        )
        createTriggers(driver)
        return driver
    }
}