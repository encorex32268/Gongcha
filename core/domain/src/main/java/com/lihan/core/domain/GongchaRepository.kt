package com.lihan.core.domain

import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface GongchaRepository {
    fun getTeas(): Flow<Result<List<Tea>, DataError>>
    fun getStores(): Flow<Result<List<Store>, DataError>>
    suspend fun setLatestSelectedStore(id: String)

    fun getLatestSelectedStore(): Flow<Store?>
}