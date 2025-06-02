package com.example.readalready_mad_project.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "Settings")


sealed class AppTheme(val name: String){
    data object Light: AppTheme("LIGHT")
    data object  Dark: AppTheme("DARK")
    data object System: AppTheme("SYSTEM")

    companion object {
        fun forName(name: String): AppTheme = when (name){
            Light.name -> Light
            Dark.name -> Dark
            System.name -> System
            else -> System
        }
    }
}

object ThemePreferences {
    val THEME_KEY = stringPreferencesKey("app_theme")

    suspend fun saveTheme(context: Context, theme: AppTheme) {
        context.dataStore.edit { prefs ->
            prefs[THEME_KEY] = theme.name
        }
    }

    fun getSavedTheme(context: Context): Flow<AppTheme> {
        return context.dataStore.data
            .map { prefs ->
                val name = prefs[THEME_KEY] ?: AppTheme.System.name
                AppTheme.forName(name)
            }
    }
}