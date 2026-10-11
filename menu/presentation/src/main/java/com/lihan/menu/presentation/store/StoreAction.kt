package com.lihan.menu.presentation.store

sealed interface StoreAction {
    data object OnNavigateBack: StoreAction
    data class OnStoreSelected(val id: String): StoreAction
}