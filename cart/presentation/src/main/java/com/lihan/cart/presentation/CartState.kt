package com.lihan.cart.presentation

import com.lihan.core.domain.model.CartItem
import com.lihan.core.domain.model.Store

data class CartState(
    val cartItems: List<CartItem> = emptyList(),
    val deleteItemContent: String? =null,
    val selectedCartItemId: Int? =null,
    val isCheckoutLoading: Boolean = false,
    val selectedStore: Store? = null
){
    val cartItemTotalPrice: Int = cartItems.sumOf { it.count * it.price}
}