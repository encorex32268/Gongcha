package com.lihan.auth.data

import com.lihan.auth.domain.AuthRepository
import com.lihan.core.domain.AppDataStore
import com.lihan.core.domain.model.User
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val appDataStore: AppDataStore
): AuthRepository {

    override val user: Flow<User?>
        get() = appDataStore.user

    override suspend fun setUser(user: User) {
        appDataStore.setUser(user)
    }
}