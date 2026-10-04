package com.lihan.menu.menu.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.menu.presentation.R
import com.lihan.menu.menu.components.TeaType.ALL
import com.lihan.menu.menu.components.TeaType.CREATIVE
import com.lihan.menu.menu.components.TeaType.MILK
import com.lihan.menu.menu.components.TeaType.ORIGINAL
import com.lihan.menu.menu.components.TeaType.SMOOTHIE

enum class TeaType {
    ALL,ORIGINAL,MILK,CREATIVE,SMOOTHIE;
}

@Composable
fun TeaType.toStringResource(): String {
    return when(this){
        ALL -> stringResource(R.string.all)
        ORIGINAL -> stringResource(R.string.original)
        MILK -> stringResource(R.string.milk)
        CREATIVE -> stringResource(R.string.creative)
        SMOOTHIE -> stringResource(R.string.smoothie)
    }
}