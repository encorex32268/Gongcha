package com.lihan.core.domain.model

data class Tea(
    val id: String = "",
    val nameZh: String,
    val nameEn: String,
    val imageUrl: String? = null,
    val price: Int = 0,
    val priceM: Int = 0,
    val priceL: Int = 0,
    val descriptionZh: String = "",
    val descriptionEn: String = "",
    val type: String = "",
)
