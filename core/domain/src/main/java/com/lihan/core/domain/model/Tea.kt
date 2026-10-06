package com.lihan.core.domain.model

data class Tea(
    val id: String,
    val nameZh: String,
    val nameEn: String,
    val imageUrl: String? = null,
    val price: Int,
    val priceM: Int,
    val priceL: Int,
    val descriptionZh: String,
    val descriptionEn: String,
    val type: String,
)
