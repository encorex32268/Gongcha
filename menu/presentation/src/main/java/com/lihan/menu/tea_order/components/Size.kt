package com.lihan.menu.tea_order.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.menu.presentation.R

enum class Size {
    M,L;
    companion object{
        @Composable
        fun Size.toStringResource(): String {
            return when(this){
                M -> stringResource(R.string.size_m)
                L -> stringResource(R.string.size_l)
            }
        }

        @Composable
        fun Size.toMLStringResource(): String {
            return when(this){
                M -> stringResource(R.string.size_m_ml)
                L -> stringResource(R.string.size_l_ml)
            }
        }
    }
}