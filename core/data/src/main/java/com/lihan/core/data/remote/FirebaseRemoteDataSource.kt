package com.lihan.core.data.remote


import com.lihan.core.domain.Tea
import com.lihan.core.domain.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class FirebaseRemoteDataSource(

): RemoteDataSource {

    override fun getAllTeas(): Flow<List<Tea>> {
        return emptyFlow()

    }
}