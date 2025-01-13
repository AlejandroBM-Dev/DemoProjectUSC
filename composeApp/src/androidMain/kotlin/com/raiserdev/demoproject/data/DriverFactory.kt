package com.raiserdev.demoproject.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.raiserdev.demoproject.ProjectDatabase

actual class DriverFactory(private val context: Context){
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            ProjectDatabase.Schema, context, "ProjectDatabase.db"
        )
    }
}