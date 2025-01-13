package com.raiserdev.demoproject.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.raiserdev.demoproject.ProjectDatabase
import java.io.File

actual class DriverFactory {

    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(url = "jdbc:sqlite:ProjectDatabase.db")
        if (!File("ProjectDatabase.db").exists()) {
            ProjectDatabase.Schema.create(driver)
        }
        return driver
    }
}