package com.lihan.core.network


import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.util.Result
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getTeas(): Flow<Result<List<Tea>, DataError>>
    fun getStores(): Flow<Result<List<Store>, DataError>>
    fun getVersionCode(versionKey: String): Flow<Result<Int, DataError>>
    suspend fun checkout(cartItems: List<CartItem>): EmptyResult<DataError>
}