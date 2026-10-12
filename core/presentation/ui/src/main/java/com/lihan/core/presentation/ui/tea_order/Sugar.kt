package com.lihan.core.presentation.ui.tea_order

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.core.domain.model.Sugar
import com.lihan.core.presentation.ui.R
import com.lihan.core.presentation.ui.UiText

@Composable
fun Sugar.toStringResource(): String {
    return when(this) {
        Sugar.NONE -> stringResource(R.string.sugar_none)
        Sugar.LITTLE -> stringResource(R.string.sugar_little)
        Sugar.HALF -> stringResource(R.string.sugar_half)
        Sugar.LESS -> stringResource(R.string.sugar_less)
        Sugar.FULL -> stringResource(R.string.sugar_full)
    }
}

fun Sugar.toUiText(): UiText {
    return when(this) {
        Sugar.NONE -> UiText.StringResource(R.string.sugar_none)
        Sugar.LITTLE -> UiText.StringResource(R.string.sugar_little)
        Sugar.HALF -> UiText.StringResource(R.string.sugar_half)
        Sugar.LESS -> UiText.StringResource(R.string.sugar_less)
        Sugar.FULL -> UiText.StringResource(R.string.sugar_full)
    }
}

fun Sugar.toPercent(): String {
    return when(this) {
        Sugar.NONE -> "0%"
        Sugar.LITTLE -> "30%"
        Sugar.HALF -> "50%"
        Sugar.LESS -> "70%"
        Sugar.FULL -> "100%"
    }
}