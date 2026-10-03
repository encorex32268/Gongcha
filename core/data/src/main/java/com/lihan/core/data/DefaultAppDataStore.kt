package com.lihan.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lihan.core.domain.AppDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class DefaultAppDataStore(
    private val context: Context
): AppDataStore {

    private companion object {
        val KEY_VERSION_CODE = intPreferencesKey("versionCode")
    }

    override val versionCode: Flow<Int> = context.appDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_VERSION_CODE] ?: 0
        }

    override suspend fun setVersionCode(code: Int) {
        context.appDataStore.edit { preferences ->
            preferences[KEY_VERSION_CODE] = code
        }
    }
}
