package com.raiserdev.demoproject

import com.raiserdev.demoproject.data.DriverFactory
import com.raiserdev.demoproject.di.appModule
import org.koin.core.context.startKoin
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
