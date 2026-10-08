package com.lihan.auth.domain

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val phoneNumber: Flow<String>
    suspend fun setPhoneNumber(phoneNumber: String)
}