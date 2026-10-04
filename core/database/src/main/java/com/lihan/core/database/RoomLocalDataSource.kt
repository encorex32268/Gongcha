package com.lihan.core.database

import com.lihan.core.database.dao.StoreDao
import com.lihan.core.database.dao.TeaDao
import com.lihan.core.database.mapper.toStore
import com.lihan.core.database.mapper.toStoreEntity
import com.lihan.core.database.mapper.toTea
import com.lihan.core.database.mapper.toTeaEntity
import com.lihan.core.domain.model.Store
import com.lihan.core.domain.model.Tea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDataSource(
    private val teaDao: TeaDao,
    private val storeDao: StoreDao
): LocalDataSource {
    override fun getTeas(): Flow<List<Tea>> {
        return teaDao.getTeas().map { entities -> entities.map { it.toTea() } }
    }

    override fun getStores(): Flow<List<Store>> {
        return storeDao.getStores().map { entities -> entities.map { it.toStore() } }
    }

    override suspend fun upsertStores(stores: List<Store>) {
        storeDao.upsertStores(stores.map { it.toStoreEntity() })
    }

    override suspend fun upsertTeas(teas: List<Tea>) {
        teaDao.upsertTeas(teas.map { it.toTeaEntity() })
    }

    override suspend fun getTeaById(id: String): Tea? {
       return teaDao.getTeaById(id)?.toTea()
    }

    override fun getStoreById(id: String): Flow<Store?> {
        return storeDao.getStoreById(id).map { it?.toStore() }
    }
}