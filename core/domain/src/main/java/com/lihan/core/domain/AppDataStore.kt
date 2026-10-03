package com.lihan.core.domain

import kotlinx.coroutines.flow.Flow

/**
 * Domain contract for app key-value persistence.
 * Pure Kotlin interface decoupled from Android/DataStore framework.
 */
interface AppDataStore {
    val versionCode: Flow<Int>
    suspend fun setVersionCode(code: Int)
}
