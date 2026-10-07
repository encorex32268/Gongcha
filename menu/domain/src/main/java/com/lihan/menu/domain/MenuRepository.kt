package com.lihan.menu.domain

import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getTeas(): Flow<List<Tea>>
    fun getStores(): Flow<List<Store>>
    suspend fun getTeaById(id: String): Tea?
    suspend fun setLatestSelectedStore(id: String)
    suspend fun getLatestSelectedStore(storeId: String): Store?
    suspend fun syncTeasData()
    suspend fun syncStoresData()
}