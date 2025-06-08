package com.raiserdev.demoproject.data

import app.cash.sqldelight.db.SqlDriver
import com.raiserdev.demoproject.ProjectDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}