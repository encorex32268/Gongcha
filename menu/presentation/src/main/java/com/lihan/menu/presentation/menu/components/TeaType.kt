package com.lihan.menu.presentation.menu.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.menu.presentation.R


enum class TeaType {
    ALL,ORIGINAL,MILK,CREATIVE,SMOOTHIE;
}

@Composable
fun TeaType.toStringResource(): String {
    return when(this){
        TeaType.ALL -> stringResource(R.string.all)
        TeaType.ORIGINAL -> stringResource(R.string.original)
        TeaType.MILK -> stringResource(R.string.milk)
        TeaType.CREATIVE -> stringResource(R.string.creative)
        TeaType.SMOOTHIE -> stringResource(R.string.smoothie)
    }
}