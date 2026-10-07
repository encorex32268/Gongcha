package com.lihan.core.network.model

data class HistoryDto(
    val id: String,
    val createAt: Long,
    val status: String,
    val orderCode: String,
    val totalPrice: Int,
    val teas: List<String>
)
