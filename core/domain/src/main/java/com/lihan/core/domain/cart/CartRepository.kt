package com.lihan.core.domain.cart

import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    val selectedStore: Flow<Store?>
    fun getCarItems(): Flow<List<CartItem>>
    fun getCartItemsByUserId(userId: String): Flow<List<CartItem>>
    fun getCarItemsCountByUserId(userId: String): Flow<Int>
    suspend fun addCartItem(cartItem: CartItem)
    suspend fun removeCartItem(cartItemId: Int)
    suspend fun updateCartItemCount(cartItemId: Int, count: Int)
    suspend fun clearCartItemsByUserId(userId: String)
    suspend fun checkout(): Result<Unit, DataError>
}