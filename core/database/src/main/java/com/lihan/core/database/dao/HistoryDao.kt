package com.lihan.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lihan.core.database.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM histories ORDER BY createAt DESC")
    fun getHistories(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM histories WHERE userId = :userId ORDER BY createAt DESC")
    fun getHistoriesByUserId(userId: String): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM histories WHERE id = :id LIMIT 1")
    suspend fun getHistoryById(id: String): HistoryEntity?

    @Upsert
    suspend fun upsertHistories(histories: List<HistoryEntity>)

    @Upsert
    suspend fun upsertHistory(history: HistoryEntity)

    @Query("DELETE FROM histories WHERE id = :id")
    suspend fun deleteHistoryById(id: String)

    @Query("DELETE FROM histories WHERE userId = :userId")
    suspend fun clearHistoriesByUserId(userId: String)

    @Query("DELETE FROM histories")
    suspend fun clearHistories()
}
