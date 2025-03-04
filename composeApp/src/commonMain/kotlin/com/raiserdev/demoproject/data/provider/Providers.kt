package com.raiserdev.demoproject.data.provider


import app.cash.sqldelight.db.SqlDriver
import com.raiserdev.demoproject.ProjectDatabase
import com.raiserdev.demoproject.data.DriverFactory
import org.koin.core.annotation.Single
/*
@Single
fun provideDriverFactory(): DriverFactory {
    return DriverFactory()
}

@Single
fun provideSqlDriver(
    driverFactory: DriverFactory
): SqlDriver {
    // Koin inyecta un DriverFactory y usas createDriver()
    return driverFactory.createDriver()
}

@Single
fun provideProjectDatabase(
    driver: SqlDriver
): ProjectDatabase {
    return ProjectDatabase(driver)
}*/