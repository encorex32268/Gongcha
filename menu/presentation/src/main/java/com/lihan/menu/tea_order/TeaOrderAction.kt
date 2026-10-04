package com.lihan.menu.tea_order

sealed interface TeaOrderAction {
    data object OnNavigateBack : TeaOrderAction
}