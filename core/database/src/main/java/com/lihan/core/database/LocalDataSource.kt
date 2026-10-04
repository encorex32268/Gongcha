package com.lihan.core.database

import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getTeas(): Flow<List<Tea>>
    fun getStores(): Flow<List<Store>>
    suspend fun upsertStores(stores: List<Store>)
    suspend fun upsertTeas(teas: List<Tea>)
    suspend fun getTeaById(id: String): Tea?
    fun getStoreById(id: String): Flow<Store?>
}