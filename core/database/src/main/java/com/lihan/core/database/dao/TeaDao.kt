package com.lihan.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lihan.core.database.entity.TeaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeaDao {
    @Query("SELECT * FROM teas")
    fun getTeas(): Flow<List<TeaEntity>>

    @Query("SELECT * FROM teas WHERE id = :id LIMIT 1")
    suspend fun getTeaById(id: String): TeaEntity?

    @Upsert
    suspend fun upsertTeas(teas: List<TeaEntity>)

    @Upsert
    suspend fun upsertTea(tea: TeaEntity)

    @Query("DELETE FROM teas")
    suspend fun clearTeas()
}
