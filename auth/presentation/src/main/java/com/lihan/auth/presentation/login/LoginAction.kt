package com.lihan.auth.presentation.login

import android.app.Activity

sealed interface LoginAction {
    data class OnContinueClick(val activity: Activity): LoginAction
    data object OnWithoutSigningClick: LoginAction
    data class OnGoogleSignIn(val activity: Activity): LoginAction
}