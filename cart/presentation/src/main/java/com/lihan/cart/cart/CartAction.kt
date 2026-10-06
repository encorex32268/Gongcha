package com.lihan.cart.cart

sealed interface CartAction {
    data class OnPlusClick(val id: Int?): CartAction
    data class OnMinusClick(val id: Int?): CartAction
    data class OnDeleteClick(val id: Int?): CartAction
    data class OnShowDeleteDialog(val text: String,val cartItemId: Int?): CartAction
    data object OnDismissDeleteDialog: CartAction
    data object OnConfirmDeleteClick: CartAction
}