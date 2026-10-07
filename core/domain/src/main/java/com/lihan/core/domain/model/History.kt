package com.lihan.core.domain.model

data class History(
    val id: String,
    val createAt: Long,
    val status: String,
    val orderCode: String,
    val totalPrice: Int,
    val teas: List<String>
)
