package com.lihan.menu.tea_order

import com.lihan.menu.tea_order.components.Topping

sealed interface TeaOrderAction {
    data object OnNavigateBack : TeaOrderAction
    data object OnPlusClick : TeaOrderAction
    data object OnMinusClick : TeaOrderAction
    data object OnCheckoutClick : TeaOrderAction
    data class OnToppingClick(val topping: Topping): TeaOrderAction
}