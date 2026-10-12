package com.lihan.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val level: MemberLevel,
    val name: String,
    val points: Int,
    val memberCode: String,
    val imageUrl: String?=null,
){

    //For Firestore Mapping
    constructor() : this(
        id = "",
        name = "",
        level = MemberLevel.NONE,
        imageUrl = null,
        points = 0,
        memberCode = ""
    )
}
