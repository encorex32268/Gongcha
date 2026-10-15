@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.menu.data

import com.lihan.core.database.LocalDataSource
import com.lihan.core.database.mapper.toStore
import com.lihan.core.database.mapper.toStoreEntity
import com.lihan.core.database.mapper.toTea
import com.lihan.core.database.mapper.toTeaEntity
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.util.onSuccess
import com.lihan.core.network.RemoteDataSource
import com.lihan.menu.domain.MenuRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MenuRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appDataStore: AppDataStore,
    private val applicationScope: CoroutineScope
): MenuRepository {

    override val selectedStore: Flow<Store?>
        get() = appDataStore
            .storeId
            .map { id ->
                localDataSource.getStoreById(id)?.toStore()
            }

    override val teas: Flow<List<Tea>>
        get() = localDataSource
            .getTeas()
            .map { entities -> entities.map { it.toTea() } }

    override val stores: Flow<List<Store>>
        get() =  localDataSource
            .getStores()
            .map { entities -> entities.map { it.toStore() } }

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
        val remoteVersionResult = remoteDataSource.getVersionCode("version_teas")

        remoteVersionResult.onSuccess { remoteVersionCode ->
            val isTeasEmpty =  localDataSource.getTeas().first().isEmpty()
            val isVersionOutdated = localVersionCode < remoteVersionCode

            if (isVersionOutdated || isTeasEmpty){
                val remoteTeasResult = remoteDataSource.getTeas()
                remoteTeasResult
                    .onSuccess { teas ->
                        applicationScope.launch {
                            localDataSource.upsertTeas(
                                teas.map { it.toTeaEntity() }
                            )
                            appDataStore.setTeasVersionCode(remoteVersionCode)
                        }.join()
                    }

            }
        }
    }
    override suspend fun syncStoresData(){
        val localVersionCode = appDataStore.storesVersionCode.first()
        val remoteVersionResult = remoteDataSource.getVersionCode("version_stores")
        remoteVersionResult.onSuccess { remoteVersionCode ->
            val isStoresEmpty =  localDataSource.getStores().first().isEmpty()
            val isVersionOutdated = localVersionCode < remoteVersionCode

            if (isVersionOutdated || isStoresEmpty){
                val remoteStoresResult = remoteDataSource.getStores()
                remoteStoresResult
                    .onSuccess { stores ->
                        applicationScope.launch {
                            localDataSource.upsertStores(
                                stores.map { it.toStoreEntity() }
                            )
                            appDataStore.setStoresVersionCode(remoteVersionCode)
                        }.join()
                    }
            }
        }
    }
}