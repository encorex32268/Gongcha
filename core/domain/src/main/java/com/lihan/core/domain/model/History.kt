package com.lihan.core.domain.model

import java.time.ZoneId

data class History(
    val id: String,
    val createAt: Long,
    val status: OrderStatus,
    val orderCode: String,
    val totalPrice: Int,
    val teas: List<String>
){
    val formattedCreateAt: String
        get(){
            val dateTime = java.time.Instant.ofEpochMilli(createAt)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            return "${dateTime.year}/${dateTime.monthValue}/${dateTime.dayOfMonth} ${dateTime.hour}:${dateTime.minute}"
        }
}
