package com.lihan.menu.presentation.tea_order

import com.lihan.core.domain.model.Ice
import com.lihan.core.domain.model.Size
import com.lihan.core.domain.model.Sugar
import com.lihan.core.domain.model.Topping

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