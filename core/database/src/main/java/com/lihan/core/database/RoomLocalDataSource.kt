package com.lihan.core.database

import com.lihan.core.database.dao.CartItemDao
import com.lihan.core.database.dao.HistoryDao
import com.lihan.core.database.dao.StoreDao
import com.lihan.core.database.dao.TeaDao
import com.lihan.core.database.entity.CartItemEntity
import com.lihan.core.database.entity.HistoryEntity
import com.lihan.core.database.entity.StoreEntity
import com.lihan.core.database.entity.TeaEntity
import kotlinx.coroutines.flow.Flow

class RoomLocalDataSource(
    private val teaDao: TeaDao,
    private val storeDao: StoreDao,
    private val cartItemDao: CartItemDao,
    private val historyDao: HistoryDao
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

    override fun getCartItemsByUserId(userId: String): Flow<List<CartItemEntity>> {
        return cartItemDao.getCartItemsByUserId(userId)
    }

    override fun getCarItemsCountByUserId(userId: String): Flow<Int> {
        return cartItemDao.getTotalItemCountByUserId(userId)
    }

    override fun getHistories(): Flow<List<HistoryEntity>> {
        return historyDao.getHistories()
    }

    override fun getHistoriesByUserId(userId: String): Flow<List<HistoryEntity>> {
        return historyDao.getHistoriesByUserId(userId)
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

    override suspend fun upsertHistories(histories: List<HistoryEntity>) {
        historyDao.upsertHistories(histories)
    }

    override suspend fun upsertHistory(history: HistoryEntity) {
        historyDao.upsertHistory(history)
    }

    override suspend fun updateCartItemCount(cartItemId: Int, count: Int) {
        cartItemDao.updateCartItemCount(cartItemId, count)
    }

    override suspend fun deleteCartItem(id: Int) {
        cartItemDao.deleteCartItem(id)
    }

    override suspend fun deleteHistoryById(id: String) {
        historyDao.deleteHistoryById(id)
    }

    override suspend fun getTeaById(id: String): TeaEntity? {
       return teaDao.getTeaById(id)
    }

    override suspend fun getStoreById(id: String): StoreEntity? {
        return storeDao.getStoreById(id)
    }

    override suspend fun getHistoryById(id: String): HistoryEntity? {
        return historyDao.getHistoryById(id)
    }

    override suspend fun clearCartItemsByUserId(userId: String) {
        cartItemDao.clearCartItemsByUserId(userId)
    }

    override suspend fun clearHistories() {
        historyDao.clearHistories()
    }

    override suspend fun clearHistoriesByUserId(userId: String) {
        historyDao.clearHistoriesByUserId(userId)
    }
}