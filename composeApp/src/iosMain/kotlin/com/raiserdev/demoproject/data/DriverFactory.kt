package com.raiserdev.demoproject.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.raiserdev.demoproject.ProjectDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(ProjectDatabase.Schema, "ProjectDatabase.db")
    }
}