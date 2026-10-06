package com.lihan.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lihan.core.database.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cartitementity")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Query("DELETE FROM cartitementity WHERE id = :id")
    suspend fun deleteCartItem(id: Int)

    @Query("DELETE FROM cartitementity")
    suspend fun clearCartItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCartItem(cartItem: CartItemEntity)

    @Query("SELECT COALESCE(SUM(count), 0) FROM CartItemEntity")
    fun getTotalItemCount(): Flow<Int>

    @Query("UPDATE cartitementity SET count = :count WHERE id = :cartItemId")
    suspend fun updateCartItemCount(cartItemId: Int, count: Int)

}