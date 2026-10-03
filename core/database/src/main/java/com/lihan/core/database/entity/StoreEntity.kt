package com.lihan.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lihan.core.domain.model.BusinessHour

@Entity(tableName = "stores")
data class StoreEntity(
    @PrimaryKey
    val id: String,
    val region: String,
    val name: String,
    val address: String,
    val displayHours: String,
    val phoneNumber: String,
    val businessHours: List<BusinessHour>
)
