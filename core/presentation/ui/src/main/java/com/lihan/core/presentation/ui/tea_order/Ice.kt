package com.lihan.core.presentation.ui.tea_order

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.core.presentation.ui.R

enum class Ice {
    NO,LITTLE,LESS,REGULAR;
    companion object{
        @Composable
        fun Ice.toStringResource(): String {
            return when(this){
                NO -> stringResource(R.string.ice_no_ice)
                LITTLE -> stringResource(R.string.ice_little)
                LESS -> stringResource(R.string.ice_less)
                REGULAR -> stringResource(R.string.ice_regular)
            }
        }

        fun Ice.toSupportText(): String {
            return when(this){
                NO -> "No Ice"
                LITTLE -> "Little"
                LESS -> "Less"
                REGULAR -> "Regular"
            }
        }
    }
}