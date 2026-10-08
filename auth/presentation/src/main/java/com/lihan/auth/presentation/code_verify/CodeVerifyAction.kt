package com.lihan.auth.presentation.code_verify

import android.app.Activity

sealed interface CodeVerifyAction {
    data object OnWithoutSigningClick: CodeVerifyAction
    data class OnConfirmClick(val activity: Activity?): CodeVerifyAction
    data class OnResendSMSCode(val activity: Activity?): CodeVerifyAction
}