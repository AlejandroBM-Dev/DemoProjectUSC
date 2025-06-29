package com.raiserdev.demoproject

import androidx.compose.ui.window.ComposeUIViewController
import com.raiserdev.demoproject.data.DriverFactory
import com.raiserdev.demoproject.data.ds.dataStoreFileName
import com.raiserdev.demoproject.di.appModule
import com.raiserdev.demoproject.notas.NotasProjectDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.context.startKoin
import platform.Foundation.NSFileManager
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask

fun MainViewController() = ComposeUIViewController {
    App(
        CrossConfigDevice(),
        true
    )
}

fun initKoin() {
    startKoin {
        modules(
            appModule(
                appDatabase = NotasProjectDatabase.invoke(DriverFactory().createDriver()),
                producePath = { iosDataStorePath() })
        )
    }.koin
}

@OptIn(ExperimentalForeignApi::class)
fun iosDataStorePath(): String {
    val directory = NSFileManager.defaultManager.URLForDirectory(
        NSDocumentDirectory, NSUserDomainMask, null, false, null
    ) ?: error("No se pudo acceder al directorio Documents en iOS")
    return "${directory.path}/$dataStoreFileName"
}