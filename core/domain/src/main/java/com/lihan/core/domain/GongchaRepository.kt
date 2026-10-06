package com.lihan.core.domain

import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface GongchaRepository {
    fun getTeas(): Flow<List<Tea>>
    fun getStores(): Flow<List<Store>>
    fun getCarItems(): Flow<List<CartItem>>
    fun getCarItemsCount(): Flow<Int>
    suspend fun addCartItem(cartItem: CartItem)
    suspend fun removeCartItem(cartItemId: Int)
    suspend fun updateCartItemCount(cartItemId: Int, count: Int)
    suspend fun setLatestSelectedStore(id: String)
    suspend fun getLatestSelectedStore(storeId: String): Store?
    suspend fun getTeaById(id: String): Tea?
    suspend fun syncTeasData()
    suspend fun syncStoresData()
    suspend fun clearCartItems()
}