package com.lihan.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val teaId: String,
    val teaName: String,
    val imageUrl: String?=null,
    val storeName: String,
    val size: String,
    val toppings: List<String>,
    val sugar: String,
    val ice: String,
    val count: Int,
    val price: Int
)
