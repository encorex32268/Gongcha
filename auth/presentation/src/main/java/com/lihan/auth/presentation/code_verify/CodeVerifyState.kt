package com.lihan.auth.presentation.code_verify

import android.annotation.SuppressLint
import androidx.compose.foundation.text.input.TextFieldState
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class CodeVerifyState(
    val textFieldStates: List<TextFieldState> = (0..5).map { TextFieldState() },
    val isValid: Boolean = false,
    val isVerifyCodeTimeout: Boolean = false,
    val isCountDownTimer: Boolean = true,
    val timer: Duration = 60.seconds
){
    val timerString: String
        @SuppressLint("DefaultLocale")
        get() = timer.toComponents { _, _, minutes, seconds, _ ->
            String.format("%02d:%02d", minutes, seconds)
        }
}