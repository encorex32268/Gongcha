package com.lihan.menu.tea_order.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.menu.presentation.R

enum class Sugar {
    NONE,LITTLE,HALF,LESS,FULL;
    companion object{
        @Composable
        fun Sugar.toStringResource(): String {
            return when(this){
                NONE -> stringResource(R.string.sugar_none)
                LITTLE -> stringResource(R.string.sugar_little)
                HALF -> stringResource(R.string.sugar_half)
                LESS -> stringResource(R.string.sugar_less)
                FULL -> stringResource(R.string.sugar_full)
            }
        }

        fun Sugar.toPercent(): String {
            return when(this){
                NONE -> "0%"
                LITTLE -> "30%"
                HALF -> "50%"
                LESS -> "70%"
                FULL -> "100%"
            }
        }
    }
}