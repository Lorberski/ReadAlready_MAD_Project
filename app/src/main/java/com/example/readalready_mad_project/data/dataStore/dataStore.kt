package com.example.readalready_mad_project.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "Settings")

val THEME_KEY = stringPreferencesKey("app_theme")