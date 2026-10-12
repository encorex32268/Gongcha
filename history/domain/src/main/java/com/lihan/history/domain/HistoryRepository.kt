package com.lihan.history.domain

import com.lihan.core.domain.model.History
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistories(): Flow<List<History>>
    suspend fun cancelOrder(orderId: String): EmptyResult<DataError>
    suspend fun syncHistories()
}