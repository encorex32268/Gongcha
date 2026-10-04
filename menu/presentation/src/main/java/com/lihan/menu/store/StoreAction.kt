package com.lihan.menu.store

sealed interface StoreAction {
    data object OnNavigateBack: StoreAction
    data class OnStoreSelected(val id: String): StoreAction
}