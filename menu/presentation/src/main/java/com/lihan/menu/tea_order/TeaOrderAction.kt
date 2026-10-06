package com.lihan.menu.tea_order

import com.lihan.core.presentation.ui.model.Ice
import com.lihan.core.presentation.ui.model.Size
import com.lihan.core.presentation.ui.model.Sugar
import com.lihan.core.presentation.ui.model.Topping

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