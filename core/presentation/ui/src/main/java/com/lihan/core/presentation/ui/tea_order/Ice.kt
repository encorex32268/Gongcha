package com.lihan.core.presentation.ui.tea_order

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.core.domain.model.Ice
import com.lihan.core.presentation.ui.R
import com.lihan.core.presentation.ui.UiText

@Composable
fun Ice.toStringResource(): String {
    return when(this) {
        Ice.NO -> stringResource(R.string.ice_no_ice)
        Ice.LITTLE -> stringResource(R.string.ice_little)
        Ice.LESS -> stringResource(R.string.ice_less)
        Ice.REGULAR -> stringResource(R.string.ice_regular)
    }
}

fun Ice.toUiText(): UiText {
    return when(this) {
        Ice.NO -> UiText.StringResource(R.string.ice_no_ice)
        Ice.LITTLE -> UiText.StringResource(R.string.ice_little)
        Ice.LESS -> UiText.StringResource(R.string.ice_less)
        Ice.REGULAR -> UiText.StringResource(R.string.ice_regular)
    }
}

fun Ice.toSupportText(): String {
    return when(this) {
        Ice.NO -> "No Ice"
        Ice.LITTLE -> "Little"
        Ice.LESS -> "Less"
        Ice.REGULAR -> "Regular"
    }
}