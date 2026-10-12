package com.lihan.core.presentation.ui.tea_order

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.core.domain.model.Size
import com.lihan.core.presentation.ui.R
import com.lihan.core.presentation.ui.UiText

@Composable
fun Size.toStringResource(): String {
    return when(this) {
        Size.M -> stringResource(R.string.size_m)
        Size.L -> stringResource(R.string.size_l)
    }
}

fun Size.toUiText(): UiText {
    return when(this) {
        Size.M -> UiText.StringResource(R.string.size_m)
        Size.L -> UiText.StringResource(R.string.size_l)
    }
}

@Composable
fun Size.toMLStringResource(): String {
    return when(this) {
        Size.M -> stringResource(R.string.size_m_ml)
        Size.L -> stringResource(R.string.size_l_ml)
    }
}

fun Size.toMLUiText(): UiText {
    return when(this) {
        Size.M -> UiText.StringResource(R.string.size_m_ml)
        Size.L -> UiText.StringResource(R.string.size_l_ml)
    }
}