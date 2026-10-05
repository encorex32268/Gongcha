package com.lihan.core.domain

import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface GongchaRepository {
    fun getTeas(): Flow<List<Tea>>
    fun getStores(): Flow<List<Store>>
    suspend fun setLatestSelectedStore(id: String)
    suspend fun getLatestSelectedStore(storeId: String): Store?
    suspend fun getTeaById(id: String): Tea?
    suspend fun syncTeasData()
    suspend fun syncStoresData()
}