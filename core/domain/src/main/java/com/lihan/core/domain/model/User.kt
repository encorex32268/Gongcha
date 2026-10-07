package com.lihan.core.domain.model

data class User(
    val level: MemberLevel,
    val name: String,
    val imageUrl: String?,
    val points: Int,
    val memberCode: String,
)
