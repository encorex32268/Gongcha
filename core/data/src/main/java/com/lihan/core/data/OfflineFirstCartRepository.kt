@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.core.data

import com.lihan.core.database.LocalDataSource
import com.lihan.core.database.mapper.toCartItem
import com.lihan.core.database.mapper.toCartItemEntity
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.cart.CartRepository
import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.EmptyResult
import com.lihan.core.domain.util.Result
import com.lihan.core.network.RemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class OfflineFirstCartRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appDataStore: AppDataStore,
): CartRepository {

    override fun getCarItems(): Flow<List<CartItem>> {
        return localDataSource
            .getCartItems()
            .map { entities -> entities.map { it.toCartItem() } }
    }

    override fun getCarItemsCount(): Flow<Int> {
        return localDataSource.getCarItemsCount()
    }

    override suspend fun addCartItem(cartItem: CartItem) {
        localDataSource.upsertCartItem(cartItem.toCartItemEntity())
    }

    override suspend fun removeCartItem(cartItemId: Int) {
        localDataSource.deleteCartItem(cartItemId)
    }

    override suspend fun updateCartItemCount(cartItemId: Int, count: Int) {
        localDataSource.updateCartItemCount(cartItemId,count)
    }

    override suspend fun clearCartItems() {
        localDataSource.clearCartItems()
    }

    override suspend fun checkout(): EmptyResult<DataError> {
        val cartItems = localDataSource
            .getCartItems()
            .first()
            .map { it.toCartItem() }

        val user = appDataStore.user.first()?: return Result.Error(DataError.Local.NOT_FOUND)
        return remoteDataSource.checkout(
            userId = user.id,
            cartItems = cartItems)

    }
}