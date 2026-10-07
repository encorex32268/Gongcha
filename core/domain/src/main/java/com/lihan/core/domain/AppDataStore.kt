package com.lihan.core.domain

import com.lihan.core.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Domain contract for app key-value persistence.
 * Pure Kotlin interface decoupled from Android/DataStore framework.
 */
interface AppDataStore {
    val teasVersionCode: Flow<Int>
    suspend fun setTeasVersionCode(code: Int)

    val storesVersionCode: Flow<Int>
    suspend fun setStoresVersionCode(code: Int)

    val storeId: Flow<String>
    suspend fun setLatestSelectedStoreId(id: String)

    suspend fun setUser(user: User)
    val user: Flow<User?>
}
