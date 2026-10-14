package com.lihan.member.presentation.member

import android.graphics.Bitmap
import com.lihan.core.domain.model.User

data class MemberState(
    val isLoading: Boolean = true,
    val user: User?=null,
    val isShowQRCode: Boolean = false,
    val qrCode: Bitmap?=null
)