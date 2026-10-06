package com.lihan.core.database

import com.lihan.core.database.dao.CartItemDao
import com.lihan.core.database.dao.StoreDao
import com.lihan.core.database.dao.TeaDao
import com.lihan.core.database.entity.CartItemEntity
import com.lihan.core.database.entity.StoreEntity
import com.lihan.core.database.entity.TeaEntity
import com.lihan.core.database.mapper.toStore
import com.lihan.core.database.mapper.toStoreEntity
import com.lihan.core.database.mapper.toTea
import com.lihan.core.database.mapper.toTeaEntity
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDataSource(
    private val teaDao: TeaDao,
    private val storeDao: StoreDao,
    private val cartItemDao: CartItemDao
): LocalDataSource {

    override fun getTeas(): Flow<List<TeaEntity>> {
        return teaDao.getTeas()
    }

    override fun getStores(): Flow<List<StoreEntity>> {
        return storeDao.getStores()
    }

    override fun getCartItems(): Flow<List<CartItemEntity>> {
        return cartItemDao.getCartItems()
    }

    override fun getCarItemsCount(): Flow<Int> {
        return cartItemDao.getTotalItemCount()
    }

    override suspend fun upsertStores(stores: List<StoreEntity>) {
        storeDao.upsertStores(stores)
    }

    override suspend fun upsertTeas(teas: List<TeaEntity>) {
        teaDao.upsertTeas(teas)
    }

    override suspend fun upsertCartItem(cartItem: CartItemEntity) {
        cartItemDao.upsertCartItem(cartItem)
    }

    override suspend fun updateCartItemCount(cartItemId: Int, count: Int) {
        cartItemDao.updateCartItemCount(cartItemId, count)
    }

    override suspend fun deleteCartItem(id: Int) {
        cartItemDao.deleteCartItem(id)
    }

    override suspend fun getTeaById(id: String): TeaEntity? {
       return teaDao.getTeaById(id)
    }

    override suspend fun getStoreById(id: String): StoreEntity? {
        return storeDao.getStoreById(id)
    }

    override suspend fun clearCartItems() {
        cartItemDao.clearCartItems()
    }
}