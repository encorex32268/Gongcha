package com.lihan.auth.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    val textFieldState: TextFieldState = TextFieldState(),
    val isPhoneNumberValid: Boolean = false
)