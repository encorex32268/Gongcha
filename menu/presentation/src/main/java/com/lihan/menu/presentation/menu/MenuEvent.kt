package com.lihan.menu.presentation.menu

sealed interface MenuEvent {
    data object OnSendStorePickWarning: MenuEvent
    data class OnNavigateToTeaOrder(val teaId: String): MenuEvent
    data object OnSignOut: MenuEvent
    data object OnNavigateToSelectStore: MenuEvent
}