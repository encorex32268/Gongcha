package com.lihan.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import com.lihan.core.presentation.ui.UiText

data class LoginState(
    val errorMessage: UiText?=null,
    val isVerifying: Boolean = false
)