package com.lihan.core.presentation.ui.tea_order

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.core.domain.model.Topping
import com.lihan.core.presentation.ui.R
import com.lihan.core.presentation.ui.UiText

val Topping.nameZhResId: Int
    @StringRes
    get() = when (this) {
        Topping.PEARL -> R.string.topping_pearl_zh
        Topping.HERBAL_JELLY -> R.string.topping_herbal_jelly_zh
        Topping.AIYU_JELLY -> R.string.topping_aiyu_jelly_zh
        Topping.BROWN_SUGAR -> R.string.topping_brown_sugar_zh
        Topping.AGAR_JELLY -> R.string.topping_agar_zh
        Topping.COCONUT_JELLY -> R.string.topping_coconut_jelly_zh
        Topping.MILK_FOAM -> R.string.topping_milk_foam_zh
    }

val Topping.nameEnResId: Int
    @StringRes
    get() = when (this) {
        Topping.PEARL -> R.string.topping_pearl_en
        Topping.HERBAL_JELLY -> R.string.topping_herbal_jelly_en
        Topping.AIYU_JELLY -> R.string.topping_aiyu_jelly_en
        Topping.BROWN_SUGAR -> R.string.topping_brown_sugar_en
        Topping.AGAR_JELLY -> R.string.topping_agar_en
        Topping.COCONUT_JELLY -> R.string.topping_coconut_jelly_en
        Topping.MILK_FOAM -> R.string.topping_milk_foam_en
    }

@Composable
fun Topping.toNameZh(): String = stringResource(nameZhResId)

@Composable
fun Topping.toNameEn(): String = stringResource(nameEnResId)

@Composable
fun Topping.toStringResource(): String = stringResource(nameZhResId)

fun Topping.toNameZhUiText(): UiText = UiText.StringResource(nameZhResId)

fun Topping.toNameEnUiText(): UiText = UiText.StringResource(nameEnResId)

fun Topping.toUiText(): UiText = UiText.StringResource(nameZhResId)