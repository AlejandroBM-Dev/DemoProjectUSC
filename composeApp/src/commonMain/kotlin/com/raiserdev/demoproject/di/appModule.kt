package com.raiserdev.demoproject.di

import com.raiserdev.demoproject.ProjectDatabase
import com.raiserdev.demoproject.data.UserRepoImpl
import com.raiserdev.demoproject.domain.NotasRepository
import com.raiserdev.demoproject.ui.presentation.home.HomeViewModel
import com.raiserdev.demoproject.ui.presentation.home.SettingsViewModel
import com.raiserdev.demoproject.ui.presentation.login.HelpViewModel
import com.raiserdev.demoproject.ui.presentation.login.LoginViewModel
import com.raiserdev.demoproject.ui.presentation.login.RegisterViewModel
import org.koin.dsl.module

fun appModule(appDatabase: ProjectDatabase) = module {
    single<NotasRepository> {UserRepoImpl(appDatabase)}

    //VIEW MODELS
    factory { HelpViewModel() }
    factory { LoginViewModel() }
    factory { RegisterViewModel(get()) }
    factory { HomeViewModel() }
    factory { SettingsViewModel() }
}