package com.lihan.core.domain.auth

import com.lihan.core.domain.model.User
import com.lihan.core.domain.util.DataError
import com.lihan.core.domain.util.EmptyResult
import com.lihan.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val user: Flow<User?>
    suspend fun setUser(user: User)

    suspend fun createUser(user: User): EmptyResult<DataError>
    suspend fun getUserById(id: String): Result<User?, DataError>

    suspend fun signOut()
}