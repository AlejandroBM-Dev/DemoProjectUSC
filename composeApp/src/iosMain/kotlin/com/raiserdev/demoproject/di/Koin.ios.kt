package com.raiserdev.demoproject.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.raiserdev.demoproject.native")
actual class NativeModule