package com.lihan.core.presentation.ui.tea_order

import androidx.annotation.StringRes
import com.lihan.core.presentation.ui.R

enum class Topping(
    val price: Int,
    @StringRes val nameZhResId: Int,
    @StringRes val nameEnResId: Int,
){
    PEARL(
        price = 10,
        nameZhResId = R.string.topping_pearl_zh,
        nameEnResId = R.string.topping_pearl_en
    ),
    HERBAL_JELLY(
        price = 10,
        nameZhResId = R.string.topping_herbal_jelly_zh,
        nameEnResId = R.string.topping_herbal_jelly_en
    ),
    AIYU_JELLY(
        price = 10,
        nameZhResId = R.string.topping_aiyu_jelly_zh,
        nameEnResId = R.string.topping_aiyu_jelly_en
    ),
    BROWN_SUGAR(
        price = 10,
        nameZhResId = R.string.topping_brown_sugar_zh,
        nameEnResId = R.string.topping_brown_sugar_en
    ),
    AGAR_JELLY(
        price = 15,
        nameZhResId = R.string.topping_agar_zh,
        nameEnResId = R.string.topping_agar_en
    ),
    COCONUT_JELLY(
        price = 15,
        nameZhResId = R.string.topping_coconut_jelly_zh,
        nameEnResId = R.string.topping_coconut_jelly_en
    ),
    MILK_FOAM(
        price = 20,
        nameZhResId = R.string.topping_milk_foam_zh,
        nameEnResId = R.string.topping_milk_foam_en
    );

    companion object {
        val originalToppings = entries.filter { it.price == 10 }
        val flavorToppings = entries.filter { it.price > 10 }
    }
}