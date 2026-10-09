package com.lihan.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val level: MemberLevel,
    val name: String,
    val imageUrl: String?,
    val points: Int,
    val memberCode: String,
)
