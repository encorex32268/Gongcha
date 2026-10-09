package com.lihan.auth.domain

import com.lihan.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val user: Flow<User?>
    suspend fun setUser(user: User)
}