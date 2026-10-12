package com.lihan.core.domain.model

data class CartItem(
    val id: Int?=null,
    val userId: String,
    val teaId: String,
    val teaName: String,
    val imageUrl: String?=null,
    val storeName: String,
    val size: String,
    val toppings: List<String> = emptyList(),
    val sugar: String,
    val ice: String,
    val count: Int,
    val price: Int
)
