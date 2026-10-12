package com.lihan.core.database

import com.lihan.core.database.entity.CartItemEntity
import com.lihan.core.database.entity.HistoryEntity
import com.lihan.core.database.entity.StoreEntity
import com.lihan.core.database.entity.TeaEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getTeas(): Flow<List<TeaEntity>>
    fun getStores(): Flow<List<StoreEntity>>
    fun getCartItems(): Flow<List<CartItemEntity>>
    fun getCartItemsByUserId(userId: String): Flow<List<CartItemEntity>>
    fun getCarItemsCountByUserId(userId: String): Flow<Int>
    fun getHistories(): Flow<List<HistoryEntity>>
    fun getHistoriesByUserId(userId: String): Flow<List<HistoryEntity>>
    suspend fun upsertStores(stores: List<StoreEntity>)
    suspend fun upsertTeas(teas: List<TeaEntity>)
    suspend fun upsertCartItem(cartItem: CartItemEntity)
    suspend fun upsertHistories(histories: List<HistoryEntity>)
    suspend fun upsertHistory(history: HistoryEntity)
    suspend fun updateCartItemCount(cartItemId: Int, count: Int)
    suspend fun deleteCartItem(id: Int)
    suspend fun deleteHistoryById(id: String)
    suspend fun getTeaById(id: String): TeaEntity?
    suspend fun getStoreById(id: String): StoreEntity?
    suspend fun getHistoryById(id: String): HistoryEntity?
    suspend fun clearCartItemsByUserId(userId: String)
    suspend fun clearHistories()
    suspend fun clearHistoriesByUserId(userId: String)
}