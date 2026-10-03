package com.lihan.core.network


import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.util.Result
import com.lihan.core.domain.util.DataError
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getTeas(): Flow<Result<List<Tea>, DataError>>
    fun getStores(): Flow<Result<List<Store>, DataError>>
    fun getVersionCode(): Flow<Result<Int, DataError>>
}