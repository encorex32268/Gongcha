package com.lihan.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class Topping(val price: Int) {
    PEARL(price = 10),
    HERBAL_JELLY(price = 10),
    AIYU_JELLY(price = 10),
    BROWN_SUGAR(price = 10),
    AGAR_JELLY(price = 15),
    COCONUT_JELLY(price = 15),
    MILK_FOAM(price = 20);

    companion object {
        val originalToppings = entries.filter { it.price == 10 }
        val flavorToppings = entries.filter { it.price > 10 }
    }
}
