@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lihan.history.data

import com.lihan.core.database.LocalDataSource
import com.lihan.core.database.mapper.toHistory
import com.lihan.core.database.mapper.toHistoryEntity
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.model.History
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.EmptyResult
import com.lihan.core.domain.util.Result
import com.lihan.core.network.RemoteDataSource
import com.lihan.history.domain.HistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HistoryRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appDataStore: AppDataStore,
    private val applicationScope: CoroutineScope
): HistoryRepository{

    override val histories: Flow<List<History>>
        get() = appDataStore.user
            .flatMapLatest { user ->
                localDataSource.getHistoriesByUserId(user?.id?:"")
            }.map { entities ->
                entities.map { it.toHistory() }
            }

    override suspend fun cancelOrder(orderId: String): EmptyResult<DataError> {
        val user = appDataStore.user.first() ?: return Result.Error(DataError.Local.NOT_FOUND)
        return remoteDataSource.cancelOrder(
            userId = user.id,
            orderId = orderId
        )
    }

    override suspend fun syncHistories() {
        val user = appDataStore.user.first() ?: return
        val result = remoteDataSource.getHistories(user.id)
        val localData = localDataSource.getHistories().first()

        if (result is Result.Success){
            val isNeedUpdate = result.data.size != localData.size
            if (isNeedUpdate){
                applicationScope.launch {
                    localDataSource.clearHistories()

                    localDataSource
                        .upsertHistories(
                            result.data.map { it.toHistoryEntity(user.id) }
                        )
                }.join()
            }
        }
    }
}