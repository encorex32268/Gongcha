package com.lihan.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lihan.core.domain.model.OrderStatus

@Entity(tableName = "histories")
data class HistoryEntity(
    @PrimaryKey
    val id: String,
    val userId: String = "",
    val createAt: Long,
    val status: OrderStatus,
    val orderCode: String,
    val totalPrice: Int,
    val teas: List<String>
)
