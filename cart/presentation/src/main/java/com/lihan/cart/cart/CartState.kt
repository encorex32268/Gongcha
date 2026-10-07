package com.lihan.cart.cart

import com.lihan.core.domain.model.CartItem

data class CartState(
    val cartItems: List<CartItem> = emptyList(),
    val deleteItemContent: String? =null,
    val selectedCartItemId: Int? =null,
    val isCheckoutLoading: Boolean = false
)