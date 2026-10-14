package com.lihan.member.presentation.member

sealed interface MemberAction {
    data object OnLoginClick: MemberAction
    data object OnShowQRCode: MemberAction
    data object OnDismissQRCode: MemberAction
}