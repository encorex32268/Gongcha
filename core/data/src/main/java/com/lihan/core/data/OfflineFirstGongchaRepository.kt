@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.core.data

import com.lihan.core.database.LocalDataSource
import com.lihan.core.database.mapper.toCartItem
import com.lihan.core.database.mapper.toCartItemEntity
import com.lihan.core.database.mapper.toStore
import com.lihan.core.database.mapper.toStoreEntity
import com.lihan.core.database.mapper.toTea
import com.lihan.core.database.mapper.toTeaEntity
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.GongchaRepository
import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.Result
import com.lihan.core.domain.util.onSuccess
import com.lihan.core.network.RemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class OfflineFirstGongchaRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appDataStore: AppDataStore
): GongchaRepository {


    override fun getTeas(): Flow<List<Tea>> {
        return localDataSource
            .getTeas()
            .map { entities -> entities.map { it.toTea() } }
    }

    override fun getStores(): Flow<List<Store>> {
        return localDataSource
            .getStores()
            .map { entities -> entities.map { it.toStore() } }
    }

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

    override suspend fun setLatestSelectedStore(id: String) {
        appDataStore.setLatestSelectedStoreId(id)
    }

    override suspend fun getLatestSelectedStore(storeId: String): Store? {
        return localDataSource.getStoreById(storeId)?.toStore()
    }

    override suspend fun getTeaById(id: String): Tea? {
        return localDataSource.getTeaById(id)?.toTea()
    }


    override suspend fun syncTeasData(){
        val localVersionCode = appDataStore.teasVersionCode.first()
        val remoteVersionResult = remoteDataSource.getVersionCode("version_teas").first()

        remoteVersionResult.onSuccess { remoteVersionCode ->
            if (localVersionCode < remoteVersionCode){
                val remoteTeasResult = remoteDataSource.getTeas().first()
                remoteTeasResult
                    .onSuccess { teas ->
                        localDataSource.upsertTeas(
                            teas.map { it.toTeaEntity() }
                        )
                        appDataStore.setTeasVersionCode(remoteVersionCode)
                    }

            }
        }
    }
    override suspend fun syncStoresData(){
        val localVersionCode = appDataStore.storesVersionCode.first()
        val remoteVersionResult = remoteDataSource.getVersionCode("version_stores").first()
        remoteVersionResult.onSuccess { remoteVersionCode ->
            if (localVersionCode < remoteVersionCode){
                val remoteStoresResult = remoteDataSource.getStores().first()
                remoteStoresResult
                    .onSuccess { stores ->
                        localDataSource.upsertStores(
                            stores.map { it.toStoreEntity() }
                        )
                        appDataStore.setTeasVersionCode(remoteVersionCode)
                    }
            }
        }
    }

    override suspend fun clearCartItems() {
        localDataSource.clearCartItems()
    }

}