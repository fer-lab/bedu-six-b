package com.randomx.travel.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStoreManagerUtils constructor(context: Context) {

        companion object {
            private const val PREFERENCES_NAME = "com.randomx.travel.datastore"
            private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)
            private var instance: DataStoreManagerUtils? = null

            fun init(context: Context) {
                instance = DataStoreManagerUtils(context.applicationContext)
            }

            @Synchronized
            fun getInstance(context: Context): DataStoreManagerUtils {
                if (instance == null) {
                    init(context)
                }
                return instance as DataStoreManagerUtils
            }
        }

        private val dataStore: DataStore<Preferences> = context.dataStore


        suspend fun setString(keyString: String, value: String) {
            val key = stringPreferencesKey(keyString)
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        }

        suspend fun getString(keyString: String, defaultValue: String): String {
            val key = stringPreferencesKey(keyString)
            val preferences = dataStore.data.first()
            return preferences[key] ?: defaultValue
        }

        suspend fun removeString(keyString: String) {
            val key = stringPreferencesKey(keyString)
            dataStore.edit { preferences ->
                preferences.remove(key)
            }
        }

        suspend fun setBool(keyString: String, value: Boolean) {
            val key = booleanPreferencesKey(keyString)
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        }

        suspend fun getBool(keyString: String, defaultValue: Boolean): Boolean {
            val key = booleanPreferencesKey(keyString)
            val preferences = dataStore.data.first()
            return preferences[key] ?: defaultValue
        }

        suspend fun removeBool(keyString: String) {
            val key = booleanPreferencesKey(keyString)
            dataStore.edit { preferences ->
                preferences.remove(key)
            }
        }

        suspend fun setInt(keyString: String, value: Int) {
            val key = intPreferencesKey(keyString)
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        }

        suspend fun getInt(keyString: String, defaultValue: Int): Int {
            val key = intPreferencesKey(keyString)
            val preferences = dataStore.data.first()
            return preferences[key] ?: defaultValue
        }

        suspend fun removeInt(keyString: String) {
            val key = intPreferencesKey(keyString)
            dataStore.edit { preferences ->
                preferences.remove(key)
            }
        }

}
