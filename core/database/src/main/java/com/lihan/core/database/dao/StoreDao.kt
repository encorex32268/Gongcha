package com.lihan.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lihan.core.database.entity.StoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    @Query("SELECT * FROM stores")
    fun getStores(): Flow<List<StoreEntity>>

    @Query("SELECT * FROM stores WHERE id = :id LIMIT 1")
    suspend fun getStoreById(id: String): StoreEntity?

    @Upsert
    suspend fun upsertStores(stores: List<StoreEntity>)

    @Upsert
    suspend fun upsertStore(store: StoreEntity)

    @Query("DELETE FROM stores")
    suspend fun clearStores()
}
