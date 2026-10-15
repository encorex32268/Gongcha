package com.lihan.auth.presentation.login

sealed interface LoginEvent {
    data object OnNavigateToMenu: LoginEvent
}