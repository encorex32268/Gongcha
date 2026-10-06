package com.lihan.cart.cart

import com.lihan.core.domain.model.CartItem

data class CartState(
    val cartItems: List<CartItem> = emptyList(),
    val deleteItemContent: String? =null,
)