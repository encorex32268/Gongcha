package com.lihan.core.domain

import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCarItems(): Flow<List<CartItem>>
    fun getCarItemsCount(): Flow<Int>
    suspend fun addCartItem(cartItem: CartItem)
    suspend fun removeCartItem(cartItemId: Int)
    suspend fun updateCartItemCount(cartItemId: Int, count: Int)
    suspend fun clearCartItems()
    suspend fun checkout(): Result<Unit, DataError>
}