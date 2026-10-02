package com.lihan.core.domain

data class Store(
    val id: String,
    val region: String,
    val name: String,
    val address: String,
    val displayHours: String,
    val phoneNumber: String,
    val businessHours: List<BusinessHour>
)

data class BusinessHour(
    val day: Int,
    val open: String,
    val close: String
)
