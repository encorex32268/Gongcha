@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.core.data

import com.lihan.core.database.LocalDataSource
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.GongchaRepository
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

class GongchaRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appDataStore: AppDataStore
): GongchaRepository {

    override fun getTeas(): Flow<Result<List<Tea>, DataError>> {
        return localDataSource
            .getTeas()
            .onStart {
                syncTeasData()
            }.catch {
                Result.Error(DataError.Local.UNKNOWN)
            }
            .map {
                Result.Success(it)
            }
    }

    override fun getStores(): Flow<Result<List<Store>, DataError>> {
        return localDataSource
            .getStores()
            .onStart {
                syncStoresData()
            }.catch {
                Result.Error(DataError.Local.UNKNOWN)
            }
            .map {
                Result.Success(it)
            }
    }

    override suspend fun setLatestSelectedStore(id: String) {
        appDataStore.setLatestSelectedStoreId(id)
    }

    override fun getLatestSelectedStore(): Flow<Store?> {
        return appDataStore
            .storeId
            .distinctUntilChanged()
            .flatMapLatest { id ->
                localDataSource.getStoreById(id)
            }
    }


    private suspend fun syncTeasData(){
        val localVersionCode = appDataStore.teasVersionCode.first()
        val remoteVersionResult = remoteDataSource.getVersionCode("version_teas").first()

        remoteVersionResult.onSuccess { remoteVersionCode ->
            if (localVersionCode < remoteVersionCode){
                val remoteTeasResult = remoteDataSource.getTeas().first()
                remoteTeasResult
                    .onSuccess {
                        localDataSource.upsertTeas(it)
                        appDataStore.setTeasVersionCode(remoteVersionCode)
                    }

            }
        }
    }
    private suspend fun syncStoresData(){
        val localVersionCode = appDataStore.storesVersionCode.first()
        val remoteVersionResult = remoteDataSource.getVersionCode("version_stores").first()
        remoteVersionResult.onSuccess { remoteVersionCode ->
            if (localVersionCode < remoteVersionCode){
                val remoteStoresResult = remoteDataSource.getStores().first()
                remoteStoresResult
                    .onSuccess {
                        localDataSource.upsertStores(it)
                        appDataStore.setTeasVersionCode(remoteVersionCode)
                    }
            }
        }
    }

}