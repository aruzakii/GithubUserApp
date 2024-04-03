package com.example.githubuser.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferencecs private constructor(private val dataStore:DataStore<Preferences>){
    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        dataStore.edit { preference ->
            preference[THEME_KEY] = isDarkModeActive
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: SettingPreferencecs? = null


        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferencecs{
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferencecs(dataStore)
                INSTANCE = instance
                instance
            }

        }
    }
}