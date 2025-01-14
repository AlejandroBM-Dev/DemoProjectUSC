package com.raiserdev.demoproject.di

import com.raiserdev.demoproject.ui.presentation.home.HomeViewModel
import com.raiserdev.demoproject.ui.presentation.home.SettingsViewModel
import com.raiserdev.demoproject.ui.presentation.login.HelpViewModel
import com.raiserdev.demoproject.ui.presentation.login.LoginViewModel
import com.raiserdev.demoproject.ui.presentation.login.RegisterViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class ViewModelModule {
    @Factory
    fun provideLoginViewModel(): LoginViewModel = LoginViewModel(/* dependencias necesarias */)

    @Factory
    fun provideRegisterViewModel(): RegisterViewModel = RegisterViewModel(/* dependencias necesarias */)

    @Factory
    fun provideHelpViewModel(): HelpViewModel = HelpViewModel(/* dependencias necesarias */)

    @Factory
    fun provideHomeViewModel(): HomeViewModel = HomeViewModel(/* dependencias necesarias */)

    @Factory
    fun provideSettingsViewModel(): SettingsViewModel = SettingsViewModel(/* dependencias necesarias */)
}