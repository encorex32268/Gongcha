package com.lihan.core.network


import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.model.History
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import com.lihan.core.domain.model.User
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.EmptyResult
import com.lihan.core.domain.util.Result

interface RemoteDataSource {
    suspend fun getTeas(): Result<List<Tea>, DataError>
    suspend fun getStores(): Result<List<Store>, DataError>
    suspend fun getVersionCode(versionKey: String): Result<Int, DataError>
    suspend fun getHistories(userId: String): Result<List<History>, DataError>
    suspend fun checkout(userId: String ,cartItems: List<CartItem>): EmptyResult<DataError>
    suspend fun createUser(user: User): EmptyResult<DataError>
    suspend fun getUserById(id: String): Result<User?, DataError>
    suspend fun cancelOrder(userId: String,orderId: String): EmptyResult<DataError>
}