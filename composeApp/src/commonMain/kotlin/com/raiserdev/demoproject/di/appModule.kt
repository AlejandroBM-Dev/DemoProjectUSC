package com.raiserdev.demoproject.di

import com.raiserdev.demoproject.ProjectDatabase
import com.raiserdev.demoproject.data.UserRepoImpl
import com.raiserdev.demoproject.data.ds.PrefsDataStore
import com.raiserdev.demoproject.data.ds.UserPreferencesRepository
import com.raiserdev.demoproject.data.ds.createDataStore
import com.raiserdev.demoproject.domain.UserRepository
import com.raiserdev.demoproject.ui.presentation.home.HomeViewModel
import com.raiserdev.demoproject.ui.presentation.home.NotasViewModel
import com.raiserdev.demoproject.ui.presentation.home.SettingsViewModel
import com.raiserdev.demoproject.ui.presentation.login.HelpViewModel
import com.raiserdev.demoproject.ui.presentation.login.LoginViewModel
import com.raiserdev.demoproject.ui.presentation.login.RegisterViewModel
import org.koin.dsl.module

fun appModule(
    appDatabase: ProjectDatabase,
    producePath: () -> String
) = module {

    //DATASTORE
    single<PrefsDataStore> { createDataStore(producePath) }
    //DataSTORE REPOSITORY
    single<UserPreferencesRepository> { UserPreferencesRepository(get()) }

    //REPOSITORY
    single<UserRepository> { UserRepoImpl(appDatabase) }

    //VIEW MODELS
    factory { HelpViewModel() }
    factory { LoginViewModel(get(), get()) }
    factory { RegisterViewModel(get()) }
    factory { HomeViewModel(get()) }
    factory { NotasViewModel() }
    factory { SettingsViewModel() }
}