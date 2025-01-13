package com.raiserdev.demoproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform