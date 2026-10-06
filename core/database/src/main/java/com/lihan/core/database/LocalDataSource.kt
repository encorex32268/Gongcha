package com.lihan.core.database

import com.lihan.core.database.entity.CartItemEntity
import com.lihan.core.database.entity.StoreEntity
import com.lihan.core.database.entity.TeaEntity
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getTeas(): Flow<List<TeaEntity>>
    fun getStores(): Flow<List<StoreEntity>>
    fun getCartItems(): Flow<List<CartItemEntity>>
    fun getCarItemsCount(): Flow<Int>
    suspend fun upsertStores(stores: List<StoreEntity>)
    suspend fun upsertTeas(teas: List<TeaEntity>)
    suspend fun upsertCartItem(cartItem: CartItemEntity)
    suspend fun updateCartItemCount(cartItemId: Int, count: Int)
    suspend fun deleteCartItem(id: Int)
    suspend fun getTeaById(id: String): TeaEntity?
    suspend fun getStoreById(id: String): StoreEntity?
    suspend fun clearCartItems()
}