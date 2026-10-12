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
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.map

class MenuRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appDataStore: AppDataStore,
    private val applicationScope: CoroutineScope
): MenuRepository {

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
        val remoteVersionResult = remoteDataSource.getVersionCode("version_stores").first()
        remoteVersionResult.onSuccess { remoteVersionCode ->
            if (localVersionCode < remoteVersionCode){
                val remoteStoresResult = remoteDataSource.getStores().first()
                remoteStoresResult
                    .onSuccess { stores ->
                        applicationScope.launch {
                            localDataSource.upsertStores(
                                stores.map { it.toStoreEntity() }
                            )
                            appDataStore.setStoresVersionCode(remoteVersionCode)
                        }
                    }
            }
        }
    }
}