package com.lihan.menu.presentation.tea_order

import com.lihan.core.presentation.ui.tea_order.Ice
import com.lihan.core.presentation.ui.tea_order.Size
import com.lihan.core.presentation.ui.tea_order.Sugar
import com.lihan.core.presentation.ui.tea_order.Topping

sealed interface TeaOrderAction {
    data object OnNavigateBack : TeaOrderAction
    data object OnPlusClick : TeaOrderAction
    data object OnMinusClick : TeaOrderAction
    data object OnAddToCartClick : TeaOrderAction
    data class OnToppingClick(val topping: Topping): TeaOrderAction
    data class OnSizeSelect(val size: Size): TeaOrderAction
    data class OnSugarSelect(val sugar: Sugar): TeaOrderAction
    data class OnIceSelect(val ice: Ice): TeaOrderAction
}