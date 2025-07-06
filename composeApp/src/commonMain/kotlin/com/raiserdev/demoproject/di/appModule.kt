package com.raiserdev.demoproject.di

import com.raiserdev.demoproject.data.db.repository.NotaRepoImpl
import com.raiserdev.demoproject.data.db.repository.UserRepoImpl
import com.raiserdev.demoproject.data.ds.PrefsDataStore
import com.raiserdev.demoproject.data.ds.UserPreferencesRepository
import com.raiserdev.demoproject.data.ds.createDataStore
import com.raiserdev.demoproject.domain.NotasRepository
import com.raiserdev.demoproject.domain.UserRepository
import com.raiserdev.demoproject.notas.NotasProjectDatabase
import com.raiserdev.demoproject.ui.presentation.home.HomeViewModel
import com.raiserdev.demoproject.ui.presentation.home.NotasViewModel
import com.raiserdev.demoproject.ui.presentation.home.SettingsViewModel
import com.raiserdev.demoproject.ui.presentation.home.UserEditViewModel
import com.raiserdev.demoproject.ui.presentation.login.HelpViewModel
import com.raiserdev.demoproject.ui.presentation.login.LoginViewModel
import com.raiserdev.demoproject.ui.presentation.login.RegisterViewModel
import org.koin.dsl.module

fun appModule(
    appDatabase: NotasProjectDatabase,
    producePath: () -> String
) = module {

    //DATASTORE
    single<PrefsDataStore> { createDataStore(producePath) }
    //DataSTORE REPOSITORY
    single<UserPreferencesRepository> { UserPreferencesRepository(get()) }

    //REPOSITORY
    single<UserRepository> { UserRepoImpl(appDatabase) }
    single<NotasRepository> { NotaRepoImpl(appDatabase) }

    //VIEW MODELS
    factory { HelpViewModel() }
    factory { LoginViewModel(get(), get()) }
    factory { RegisterViewModel(get()) }
    factory { HomeViewModel(get(), get()) }
    factory { NotasViewModel(get(),get()) }
    factory { UserEditViewModel(get(),get()) }
    factory { SettingsViewModel() }
}