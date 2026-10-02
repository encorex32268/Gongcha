package com.lihan.core.domain

data class Tea(
    val id: String = "",
    val nameZh: String,
    val nameEn: String,
    val imageUrl: String? = null,
    val imageRes: Int? = null,
    val price: Int = 0,
    val descriptionZh: String = "",
    val descriptionEn: String = ""
)
