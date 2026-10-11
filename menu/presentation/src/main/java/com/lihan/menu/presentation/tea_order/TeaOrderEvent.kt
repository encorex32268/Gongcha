package com.lihan.menu.presentation.tea_order

sealed interface TeaOrderEvent {
    data object OnSavedCartItem: TeaOrderEvent
}