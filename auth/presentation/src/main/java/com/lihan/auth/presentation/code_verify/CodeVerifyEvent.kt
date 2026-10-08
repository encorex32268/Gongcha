package com.lihan.auth.presentation.code_verify

sealed interface CodeVerifyEvent {
    data object OnNavigateToMenu: CodeVerifyEvent
}