package com.lihan.menu.tea_order

import com.lihan.core.domain.model.Tea
import com.lihan.core.presentation.ui.model.Ice
import com.lihan.core.presentation.ui.model.Size
import com.lihan.core.presentation.ui.model.Sugar
import com.lihan.core.presentation.ui.model.Topping


data class TeaOrderState(
    val tea: Tea? = null,
    val size: Size = Size.M,
    val toppings: List<Topping> = emptyList(),
    val sugar: Sugar?= null,
    val ice: Ice?=null,
    val count: Int = 1
){
    val canCheckout: Boolean
        get() = sugar != null && ice != null && price != 0

    val price: Int
        get() {
            return when(size){
                Size.M -> tea?.priceM?:0
                Size.L -> tea?.priceL?:0
            }
        }

    val totalPrice: Int
        get(){
            val toppingsPrice = toppings.sumOf { it.price }
            val onePrice = price + toppingsPrice
            return onePrice * count
        }
}