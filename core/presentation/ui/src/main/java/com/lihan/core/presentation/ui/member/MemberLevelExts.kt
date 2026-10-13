package com.lihan.core.presentation.ui.member

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import com.lihan.core.domain.model.MemberLevel
import com.lihan.core.domain.model.MemberLevel.COPPER
import com.lihan.core.domain.model.MemberLevel.GOLD
import com.lihan.core.domain.model.MemberLevel.NONE
import com.lihan.core.domain.model.MemberLevel.SILVER
import com.lihan.core.presentation.designsystem.ui.theme.CopperBackgroundGradient
import com.lihan.core.presentation.designsystem.ui.theme.GoldBackgroundGradient
import com.lihan.core.presentation.designsystem.ui.theme.SilverBackgroundGradient
import com.lihan.core.presentation.designsystem.ui.theme.StandardBackgroundGradient
import com.lihan.core.presentation.ui.R

fun MemberLevel.toLevelImageResId(): Int {
    return when(this){
        NONE -> R.drawable.level_none
        COPPER -> R.drawable.level_copper
        SILVER -> R.drawable.level_silver
        GOLD -> R.drawable.level_gold
    }
}

@Composable
fun MemberLevel.toStringResource(): String {
    return when(this){
        NONE -> stringResource(R.string.level_none)
        COPPER -> stringResource(R.string.level_copper)
        SILVER -> stringResource(R.string.level_silver)
        GOLD -> stringResource(R.string.level_gold)
    }
}

fun MemberLevel.toGradient(): Brush {
    return when(this){
        NONE -> StandardBackgroundGradient
        COPPER -> CopperBackgroundGradient
        SILVER -> SilverBackgroundGradient
        GOLD -> GoldBackgroundGradient
    }
}