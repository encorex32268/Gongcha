package com.lihan.core.domain.datasource

import com.lihan.core.domain.Tea
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllTeas(): Flow<List<Tea>>
}