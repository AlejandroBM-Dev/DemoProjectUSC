package com.raiserdev.demoproject.native

import org.koin.core.annotation.Single

@Single
actual class PlatformComponent {
    actual fun sayHello(): String = "I'm desktop"
}