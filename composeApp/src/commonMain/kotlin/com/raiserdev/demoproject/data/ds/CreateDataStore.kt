package com.raiserdev.demoproject.data.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

typealias PrefsDataStore = DataStore<Preferences>

const val dataStoreFileName = "mx.raiserdev.preferences_notes_app.preferences_pb"

fun createDataStore(producePath: () -> String): PrefsDataStore =
    PreferenceDataStoreFactory.createWithPath {
        producePath().toPath()
    }