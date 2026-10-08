package com.lihan.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import com.lihan.core.presentation.ui.UiText

data class LoginState(
    val textFieldState: TextFieldState = TextFieldState(initialText = "0930588633"),
    val isPhoneNumberValid: Boolean = false,
    val errorMessage: UiText?=null,
    val isVerifying: Boolean = false
)