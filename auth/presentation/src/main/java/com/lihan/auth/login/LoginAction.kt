package com.lihan.auth.login

sealed interface LoginAction {
    data object OnContinueClick: LoginAction
    data object OnWithoutSigningClick: LoginAction
}