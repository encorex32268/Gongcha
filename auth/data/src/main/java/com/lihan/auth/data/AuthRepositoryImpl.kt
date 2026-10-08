package com.lihan.auth.data

import com.lihan.auth.domain.AuthRepository
import com.lihan.core.domain.AppDataStore
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val appDataStore: AppDataStore
): AuthRepository {

    override val phoneNumber: Flow<String>
        get() = appDataStore.phoneNumber

    override suspend fun setPhoneNumber(phoneNumber: String) {
        appDataStore.setPhoneNumber(phoneNumber)
    }
}