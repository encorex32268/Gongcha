package com.lihan.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.io.IOException

val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class DefaultAppDataStore(
    private val context: Context
): AppDataStore {

    private companion object {
        val KEY_TEAS_VERSION_CODE = intPreferencesKey("teasVersionCode")
        val KEY_STORES_VERSION_CODE = intPreferencesKey("storesVersionCode")
        val KEY_STORE_ID = stringPreferencesKey("storeId")
        val KEY_USER = stringPreferencesKey("user")
        val KEY_PHONE_NUMBER = stringPreferencesKey("phoneNumber")
    }

    override val teasVersionCode: Flow<Int>
        get() = context.appDataStore.data
            .map { preferences ->
                preferences[KEY_TEAS_VERSION_CODE] ?: 0
            }

    override suspend fun setTeasVersionCode(code: Int) {
        context.appDataStore.edit { preferences ->
            preferences[KEY_TEAS_VERSION_CODE] = code
        }
    }

    override val storesVersionCode: Flow<Int>
        get() = context.appDataStore.data
            .map { preferences ->
                preferences[KEY_STORES_VERSION_CODE] ?: 0
            }

    override suspend fun setStoresVersionCode(code: Int) {
        context.appDataStore.edit { preferences ->
            preferences[KEY_STORES_VERSION_CODE] = code
        }
    }

    override val storeId: Flow<String>
        get() = context.appDataStore.data
            .map { preferences ->
                preferences[KEY_STORE_ID]?:""
            }

    override suspend fun setLatestSelectedStoreId(id: String) {
        context.appDataStore.edit { preferences ->
            preferences[KEY_STORE_ID] = id
        }
    }

    override suspend fun setUser(user: User) {
        context.appDataStore.edit { preferences ->
            preferences[KEY_USER] = Json.encodeToString(user)
        }
    }

    override val phoneNumber: Flow<String>
        get() = context.appDataStore.data
            .map { preferences ->
                val phoneNumberJson = preferences[KEY_PHONE_NUMBER]?:""
                if (phoneNumberJson.isBlank()){
                    ""
                }else{
                    Json.decodeFromString(phoneNumberJson)
                }
            }

    override suspend fun setPhoneNumber(phoneNumber: String) {
        context.appDataStore.edit { preferences ->
            preferences[KEY_PHONE_NUMBER] = Json.encodeToString(phoneNumber)
        }
    }

    override suspend fun clean() {
        context.appDataStore.edit {
            it.clear()
        }
    }

    override val user: Flow<User?>
        get() = context.appDataStore.data
            .map { preferences ->
                val userJsonString = preferences[KEY_USER]?:""
                if (userJsonString.isBlank()){
                    null
                }else{
                    Json.decodeFromString<User>(userJsonString)
                }
            }
}
