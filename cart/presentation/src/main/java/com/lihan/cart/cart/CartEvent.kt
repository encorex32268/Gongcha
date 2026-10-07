package com.lihan.cart.cart

import com.lihan.core.presentation.ui.UiText

interface CartEvent {
    data object OnCheckoutSuccess: CartEvent
    data class OnCheckoutFail(val errorMessage: UiText): CartEvent
}