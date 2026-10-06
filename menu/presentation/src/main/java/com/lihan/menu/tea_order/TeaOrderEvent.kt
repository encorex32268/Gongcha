package com.lihan.menu.tea_order

sealed interface TeaOrderEvent {
    data object OnSavedCartItem: TeaOrderEvent
}