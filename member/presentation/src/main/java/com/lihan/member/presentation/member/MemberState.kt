package com.lihan.member.presentation.member

import com.lihan.core.domain.model.User

data class MemberState(
    val isLoading: Boolean = false,
    val user: User?=null
)